package com.example.baitapmobile.tuan6.nangcao

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.delay

data class PermissionItem(
    val icon: ImageVector,
    val title: String,
    val subtitle: String,
    val description: String,
    val permission: String,
    val isRequired: Boolean = true,
    val color: Color
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionRequestScreen(navController: NavHostController) {
    val context = LocalContext.current
    var selectedPermission by remember { mutableStateOf<PermissionItem?>(null) }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var showRetryDialog by remember { mutableStateOf(false) }
    var deniedPermission by remember { mutableStateOf<PermissionItem?>(null) }

    val permissions = remember {
        listOf(
            PermissionItem(
                icon = Icons.Default.LocationOn,
                title = "Location Access",
                subtitle = "Find nearby places",
                description = "Allow the app to access your device's location to provide personalized content and location-based features.",
                permission = android.Manifest.permission.ACCESS_FINE_LOCATION,
                color = Color(0xFF4CAF50)
            ),
            PermissionItem(
                icon = Icons.Default.Call,
                title = "Phone Access",
                subtitle = "Contacts access",
                description = "Allow the app to access you devices contacts",
                permission = android.Manifest.permission.CALL_PHONE,
                color = Color(0xFF2196F3)
            ),
            PermissionItem(
                icon = Icons.Default.AddCircle,
                title = "Camera Access",
                subtitle = "Scan & capture",
                description = "Access your camera to scan QR codes, take photos, and use camera-based features within the app.",
                permission = android.Manifest.permission.CAMERA,
                color = Color(0xFFFF9800)
            )
        )
    }

    // Permission launchers
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            handlePermissionResult(context, granted, "Location Access") {
                if (!granted) {
                    deniedPermission = permissions.find { it.permission == android.Manifest.permission.ACCESS_FINE_LOCATION }
                    showRetryDialog = true
                }
            }
        }
    )

    val phonePermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            handlePermissionResult(context, granted, "Phone Access") {
                if (!granted) {
                    deniedPermission = permissions.find { it.permission == android.Manifest.permission.CALL_PHONE}
                    showRetryDialog = true
                }
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            handlePermissionResult(context, granted, "Camera Access") {
                if (!granted) {
                    deniedPermission = permissions.find { it.permission == android.Manifest.permission.CAMERA }
                    showRetryDialog = true
                }
            }
        }
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFF8F9FA),
                        Color(0xFFE3F2FD)
                    )
                )
            )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(vertical = 32.dp)
        ) {
            item {
                HeaderSection()
            }

            items(permissions) { permission ->
                PermissionCard(
                    permission = permission,
                    onClick = {
                        selectedPermission = permission
                        showPermissionDialog = true
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                ActionButtons(
                    onContinue = {
                        // Request all permissions
                        permissions.forEach { permission ->
                            when (permission.permission) {
                                android.Manifest.permission.ACCESS_FINE_LOCATION -> {
                                    locationPermissionLauncher.launch(permission.permission)
                                }
                                android.Manifest.permission.CALL_PHONE -> {
                                    phonePermissionLauncher.launch(permission.permission)
                                }
                                android.Manifest.permission.CAMERA -> {
                                    cameraPermissionLauncher.launch(permission.permission)
                                }
                            }
                        }
                    },
                )
            }
        }
    }

    // Permission Detail Dialog
    if (showPermissionDialog && selectedPermission != null) {
        PermissionDetailDialog(
            permission = selectedPermission!!,
            onDismiss = { showPermissionDialog = false },
            onAllow = {
                when (selectedPermission!!.permission) {
                    android.Manifest.permission.ACCESS_FINE_LOCATION -> {
                        locationPermissionLauncher.launch(selectedPermission!!.permission)
                    }
                    android.Manifest.permission.CALL_PHONE -> {
                        phonePermissionLauncher.launch(selectedPermission!!.permission)
                    }
                    android.Manifest.permission.CAMERA -> {
                        cameraPermissionLauncher.launch(selectedPermission!!.permission)
                    }
                }
                showPermissionDialog = false
            }
        )
    }

    // Retry Permission Dialog
    if (showRetryDialog && deniedPermission != null) {
        RetryPermissionDialog(
            permission = deniedPermission!!,
            onDismiss = {
                showRetryDialog = false
                deniedPermission = null
            },
            onRetry = {
                when (deniedPermission!!.permission) {
                    android.Manifest.permission.ACCESS_FINE_LOCATION -> {
                        locationPermissionLauncher.launch(deniedPermission!!.permission)
                    }
                    android.Manifest.permission.CALL_PHONE -> {
                        phonePermissionLauncher.launch(deniedPermission!!.permission)
                    }
                    android.Manifest.permission.CAMERA -> {
                        cameraPermissionLauncher.launch(deniedPermission!!.permission)
                    }
                }
                showRetryDialog = false
                deniedPermission = null
            },
            onOpenSettings = {
                openAppSettings(context)
                showRetryDialog = false
                deniedPermission = null
            }
        )
    }
}

@Composable
private fun HeaderSection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        var isVisible by remember { mutableStateOf(false) }
        val scale by animateFloatAsState(
            targetValue = if (isVisible) 1f else 0.8f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )

        LaunchedEffect(Unit) {
            delay(100)
            isVisible = true
        }

        Text(
            text = "App Permissions",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF1A1A1A)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "We need some permissions to provide you with the best experience",
            fontSize = 16.sp,
            color = Color(0xFF666666),
            textAlign = TextAlign.Center,
            lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PermissionCard(
    permission: PermissionItem,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        animationSpec = tween(100)
    )

    Card(
        onClick = {
            isPressed = true
            onClick()
        },
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        permission.color.copy(alpha = 0.1f),
                        CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    permission.icon,
                    contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = permission.color
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = permission.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF1A1A1A)
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = permission.subtitle,
                    fontSize = 14.sp,
                    color = Color(0xFF666666)
                )
            }

            Icon(
                Icons.Default.ArrowForward,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color(0xFFBBBBBB)
            )
        }
    }

    LaunchedEffect(isPressed) {
        if (isPressed) {
            delay(100)
            isPressed = false
        }
    }
}

@Composable
private fun ActionButtons(
    onContinue: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Button(
            onClick = onContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF6200EA)
            )
        ) {
            Text(
                text = "Grant Permissions",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun PermissionDetailDialog(
    permission: PermissionItem,
    onDismiss: () -> Unit,
    onAllow: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(20.dp),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            permission.color.copy(alpha = 0.1f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        permission.icon,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = permission.color
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = permission.title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
            }
        },
        text = {
            Text(
                text = permission.description,
                fontSize = 16.sp,
                color = Color(0xFF666666),
                lineHeight = 22.sp
            )
        },
        confirmButton = {
            Button(
                onClick = onAllow,
                colors = ButtonDefaults.buttonColors(
                    containerColor = permission.color
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Allow",
                    fontWeight = FontWeight.SemiBold
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Not now",
                    color = Color(0xFF666666)
                )
            }
        }
    )
}

@Composable
private fun RetryPermissionDialog(
    permission: PermissionItem,
    onDismiss: () -> Unit,
    onRetry: () -> Unit,
    onOpenSettings: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(20.dp),
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            Color(0xFFFF5722).copy(alpha = 0.1f),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Warning,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFFFF5722)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = "Quyền bị từ chối",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A1A1A)
                )
            }
        },
        text = {
            Column {
                Text(
                    text = "Bạn đã từ chối quyền ${permission.title}. Một số tính năng có thể không hoạt động đúng cách.",
                    fontSize = 16.sp,
                    color = Color(0xFF666666),
                    lineHeight = 22.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Bạn có muốn thử lại không?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF1A1A1A)
                )
            }
        },
        confirmButton = {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onRetry,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = permission.color
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Thử lại",
                        fontWeight = FontWeight.SemiBold
                    )
                }

                OutlinedButton(
                    onClick = onOpenSettings,
                    shape = RoundedCornerShape(12.dp),
                ) {
                    Text(
                        text = "Cài đặt",
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "Bỏ qua",
                    color = Color(0xFF666666)
                )
            }
        }
    )
}

private fun handlePermissionResult(
    context: android.content.Context,
    granted: Boolean,
    permissionName: String,
    onDenied: () -> Unit = {}
) {
    if (granted) {
        Toast.makeText(context, "✅ $permissionName đã được cấp thành công!", Toast.LENGTH_SHORT).show()
    } else {
        onDenied()
    }
}

private fun openAppSettings(context: android.content.Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}
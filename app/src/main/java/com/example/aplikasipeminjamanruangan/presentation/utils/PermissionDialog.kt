package com.example.aplikasipeminjamanruangan.presentation.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        buttons = {
            Button(isPermanentlyDeclined, onOkClick, onGoToAppSettingsClick)
        },
        title = {
            Text(text = "Permission required", color = textColor)
        },
        text = {
            Text(
                text = permissionTextProvider.getDescription(isPermanentlyDeclined),
                color = textColor,
            )
        },
        modifier = Modifier
    )
}

interface PermissionTextProvider {
    fun getDescription(isPermanentlyDeclined: Boolean): String
}

class WriteExternalStorageProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "it seems you permanently declined external write permission. " +
                    "You can go to app settings to grant it"
        } else {
            "This app need access to your external write permission to download PDF"
        }
    }

}

class ReadExternalStorageProvider : PermissionTextProvider {
    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "it seems you permanently declined external read permission. " +
                    "You can go to app settings to grant it"
        } else {
            "This app need access to your external read permission to download PDF"
        }
    }

}

@Composable
fun Button(
    isPermanentlyDeclined: Boolean, onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Divider(color = Color.LightGray)
        Text(
            text = if (isPermanentlyDeclined) {
                "Grant Permission"
            } else {
                "Ok"
            },
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = textColor,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    if (isPermanentlyDeclined) {
                        onGoToAppSettingsClick()
                    } else {
                        onOkClick()
                    }
                }
                .padding(16.dp)
        )
    }
}
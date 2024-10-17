package com.jagrit.whatsmyip

import android.content.Context
import android.net.wifi.WifiManager
import android.text.format.Formatter
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.glance.ColorFilter
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.LocalContext
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider

class IPAddressWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                IPContent()
            }
        }
    }

    @Composable
    private fun IPContent() {
        var refreshCount by remember { mutableIntStateOf(0) }
        Column(
            modifier = GlanceModifier.clickable {
                actionStartActivity<MainActivity>()
            }.fillMaxSize()
                .background(GlanceTheme.colors.widgetBackground),
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val context = LocalContext.current
            val wifiManager =
                context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
            val ipAddress =
                remember(refreshCount) { Formatter.formatIpAddress(wifiManager.connectionInfo.ipAddress) }
             Text(
                text = "My IP",
                modifier = GlanceModifier.padding(12.dp),
                style = TextStyle(color = ColorProvider(Color.White))
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = ipAddress,
                    modifier = GlanceModifier.padding(12.dp),
                    style = TextStyle(color = ColorProvider(Color.White))
                )
                Image(
                    modifier = GlanceModifier.clickable {
                        refreshCount++
                    },
                    provider = ImageProvider(R.drawable.baseline_refresh_24),
                    contentDescription = "Refresh",
                    colorFilter = ColorFilter.tint(ColorProvider(Color.White))
                )
            }
        }
    }
}
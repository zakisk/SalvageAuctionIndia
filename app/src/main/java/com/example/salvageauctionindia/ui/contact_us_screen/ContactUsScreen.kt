package com.example.salvageauctionindia.ui.contact_us_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.salvageauctionindia.domain.share.ShareUtil
import com.example.salvageauctionindia.ui.common_components.LinkTextButton
import com.example.salvageauctionindia.ui.theme.LocalSpacing
import com.example.salvageauctionindia.ui.theme.PaoloVeroneseGreen

@Composable
fun ContactUsScreen() {

    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val noList = listOf("9511231872", "7385300655", "8856915355", "8975513847")
    val emailList = listOf("Email 1", "Email2", "Email3")
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(spacing.large)
            .border(0.3.dp, color = Color.LightGray)
            .background(color = PaoloVeroneseGreen.copy(alpha = 0.05f)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        item {
            Text(
                text = "CALL US",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(spacing.large)
            )
        }

        items(noList) { mobileNo ->
            LinkTextButton(text = "+91 $mobileNo", modifier = Modifier.padding(spacing.extraSmall)) {
                ShareUtil.callAdmin(context, mobileNo)
            }
        }

        item {
            Text(
                text = "EMAIL US",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.padding(spacing.large)
            )
        }

        items(emailList) { email ->
            LinkTextButton(text = email, modifier = Modifier.padding(spacing.extraSmall)) {
                ShareUtil.sendEmail(context, email)
            }
        }

        item {
            Text(
                text = "OFFICE ADDRESS",
                style = MaterialTheme.typography.h6,
                modifier = Modifier.padding(spacing.small)
            )
        }

        item {

        }
    }
}
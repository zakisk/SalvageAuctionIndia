package com.example.salvageauctionindia.ui.login_screen.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.salvageauctionindia.ui.common_components.LinkTextButton
import com.example.salvageauctionindia.ui.terms_and_conditions_screen.TermsAndConditionScreen
import com.example.salvageauctionindia.ui.theme.Apricot
import com.example.salvageauctionindia.ui.theme.LocalSpacing


@Composable
fun TAndCRow(
    modifier: Modifier,
    isChecked: MutableState<Boolean>,
) {
    val spacing = LocalSpacing.current
    var isShowDialog by remember { mutableStateOf(false) }

    Column(modifier = modifier) {
        Row {
            Checkbox(
                checked = isChecked.value,
                onCheckedChange = {
                    isChecked.value = it
                },
                colors = CheckboxDefaults.colors(checkedColor = Apricot),
                modifier = Modifier.padding(vertical = spacing.medium)
            )

            Text(
                text = "Accept The ",
                modifier = Modifier.padding(vertical = spacing.medium, horizontal = spacing.extraSmall),
                style = MaterialTheme.typography.body2,
            )

            LinkTextButton(
                text = "Terms & Conditions",
                modifier = Modifier.padding(vertical = spacing.medium)
            ) {
                isShowDialog = true
            }
        }

        if (!isChecked.value) {
            ErrorMessage(
                message = "Please Accept the Terms & Conditions",
                modifier = modifier
            )
        }
    }

    if (isShowDialog) {
        TermsAndConditionsDialog { isShowDialog = false }
    }
}


@Composable
fun TermsAndConditionsDialog(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Surface {
            TermsAndConditionScreen(listBottomSpacing = 0.dp)
        }
    }
}
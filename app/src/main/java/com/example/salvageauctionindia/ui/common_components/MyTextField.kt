package com.example.salvageauctionindia.ui.common_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.salvageauctionindia.ui.login_screen.components.ErrorMessage
import com.example.salvageauctionindia.ui.theme.Apricot
import com.example.salvageauctionindia.ui.theme.LocalCustomShapes
import com.example.salvageauctionindia.ui.theme.LocalSpacing
import com.example.salvageauctionindia.util.Constants.NO_MAX_CHARACTERS_CONSTRAINT
import com.example.salvageauctionindia.util.Constants.NO_MAX_NUMBER_CONSTRAINT
import com.example.salvageauctionindia.util.Constants.NO_MIN_CHARACTERS_CONSTRAINT
import com.example.salvageauctionindia.util.Constants.NO_MIN_NUMBER_CONSTRAINT
import com.example.salvageauctionindia.util.showToast


@Composable
fun MyTextField(
    text: MutableState<String>,
    error: MutableState<Boolean> = mutableStateOf(true),
    minCharactersConstraint: Int = NO_MIN_CHARACTERS_CONSTRAINT,
    maxCharacterConstraint: Int = NO_MAX_CHARACTERS_CONSTRAINT,
    isNumber: Boolean = false,
    minNumberConstraint: Int = NO_MIN_NUMBER_CONSTRAINT,
    maxNumberConstraint: Int = NO_MAX_NUMBER_CONSTRAINT,
    placeholderText: String,
    errorText: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    singleLine: Boolean = true,
    shape: CornerBasedShape = LocalCustomShapes.current.smallShape,
    keyboardOptions: KeyboardOptions = KeyboardOptions()
) {

    val spacing = LocalSpacing.current
    val context = LocalContext.current

    val defaultModifier = Modifier
        .fillMaxWidth()
        .padding(all = spacing.small)

    val textFieldDefaults = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = Apricot,
        cursorColor = Apricot
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = text.value,
            onValueChange = {
                if (isNumber) {
                    if (it.isNotEmpty()) {
                        try {
                            if (maxNumberConstraint != NO_MAX_NUMBER_CONSTRAINT) {
                                if (it.toInt() <= maxNumberConstraint) {
                                    text.value = it
                                }
                            } else {
                                text.value = it
                            }
                            error.value = text.value.toInt() >= minNumberConstraint

                        } catch (e: Exception) {
                            error.value = false
                            context.showToast("non-digit character")
                            text.value = text.value.dropLast(1)
                        }
                    } else {
                        text.value = it
                    }
                } else {
                    if (maxCharacterConstraint != NO_MAX_CHARACTERS_CONSTRAINT) {
                        if (it.length <= maxCharacterConstraint) {
                            text.value = it
                        }
                    } else {
                        text.value = it
                    }
                    error.value = text.value.length >= minCharactersConstraint
                }
            },
            modifier = defaultModifier,
            placeholder = { Text(placeholderText) },
            leadingIcon = leadingIcon,
            singleLine = singleLine,
            isError = !error.value,
            shape = shape,
            colors = textFieldDefaults,
            keyboardOptions = keyboardOptions
        )

        if (!error.value) {
            ErrorMessage(
                message = errorText,
                modifier = defaultModifier
            )
        }
    }

}
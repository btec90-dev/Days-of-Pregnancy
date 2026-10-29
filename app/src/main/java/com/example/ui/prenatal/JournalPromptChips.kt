package com.example.ui.prenatal

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class PromptChip(
    val labelFr: String,
    val labelEn: String,
    val templateFr: String,
    val templateEn: String
)

@Composable
fun JournalPromptChips(
    isFrench: Boolean,
    onSelectPrompt: (String) -> Unit
) {
    val prompts = listOf(
        PromptChip(
            "❤️ Battement de cœur",
            "❤️ Heartbeat Moment",
            "Aujourd'hui, j'ai entendu ton petit cœur battre à 158 bpm. Ce son régulier et puissant a rempli la pièce et nos yeux de larmes de bonheur avec ton papa...",
            "Today I listened to your little heart beating at 158 bpm. That steady, miraculous rhythm filled the room and brought tears of pure joy to your dad and me..."
        ),
        PromptChip(
            "💌 Message à bébé",
            "💌 Letter to Baby",
            "Mon cher petit prince,\nEn ce jour, je ressens tellement d'amour en te portant. Chaque semaine qui passe nous rapproche de notre première rencontre en juillet...",
            "My dearest baby boy,\nToday I carry you with so much wonder and gratitude. Every week that passes brings us closer to holding you in July..."
        ),
        PromptChip(
            "🩺 Question médicale",
            "🩺 Doctor Question",
            "Questions pour mon prochain rendez-vous obstétrical :\n1. Bilan sanguin et supplémentation en fer\n2. Sensations et tiraillements actuels\n3. Recommandations pour l'activité physique...",
            "Questions for my next prenatal visit:\n1. Blood test review & iron supplementation\n2. Current pelvic aches and sensations\n3. Activity and sleep guidance..."
        ),
        PromptChip(
            "👶 Mouvements ressentis",
            "👶 Fetal Movements",
            "Ce soir, alors que j'étais posée au calme, tu as bougé vigoureusement ! De petits coups vifs que ton papa a aussi pu sentir en posant sa main sur mon ventre...",
            "Tonight, as I was resting quietly, you fluttered and kicked with so much energy! Small wonderful taps that your daddy was also able to feel on my belly..."
        ),
        PromptChip(
            "🔬 Échographie du jour",
            "🔬 Ultrasound Scan Day",
            "Bilan de l'échographie d'aujourd'hui :\n• Clarté nucale / Morphologie : Rassurante\n• Poids estimé : En parfaite harmonie avec le terme\n• Ton profil : Déjà si paisible...",
            "Today's ultrasound scan notes:\n• Biometrics / Anatomy: Perfectly healthy\n• Estimated fetal weight: On track\n• Your sweet profile: Already peaceful and strong..."
        ),
        PromptChip(
            "🙏 Prière & Gratitude",
            "🙏 Prayer & Gratitude",
            "Gratitude infinie pour ce miracle que tu représentes après notre parcours de fécondation in vitro. Merci pour chaque cellule qui grandit en paix...",
            "Infinite gratitude for the miracle of your life after our IVF journey. Thank you for every breath and every quiet heartbeat growing in peace..."
        )
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        prompts.forEach { prompt ->
            SuggestionChip(
                onClick = {
                    onSelectPrompt(if (isFrench) prompt.templateFr else prompt.templateEn)
                },
                label = {
                    Text(
                        text = if (isFrench) prompt.labelFr else prompt.labelEn,
                        fontSize = 11.sp,
                        color = Color(0xFF78350F)
                    )
                },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    containerColor = Color(0xFFFAF4E8)
                ),
                border = SuggestionChipDefaults.suggestionChipBorder(
                    enabled = true,
                    borderColor = Color(0xFFD97706).copy(alpha = 0.4f)
                )
            )
        }
    }
}

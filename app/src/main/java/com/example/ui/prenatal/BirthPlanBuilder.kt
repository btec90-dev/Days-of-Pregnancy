package com.example.ui.prenatal

import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BirthPreference(
    val id: String,
    val categoryFr: String,
    val categoryEn: String,
    val labelFr: String,
    val labelEn: String,
    val isSelected: Boolean = true
)

@Composable
fun BirthPlanBuilder(isFrench: Boolean) {
    val context = LocalContext.current

    var preferences by remember {
        mutableStateOf(
            listOf(
                BirthPreference("p1", "Gestion de la Douleur", "Pain Management", "Souhait d'une analgésie péridurale posée dès que nécessaire", "Epidural analgesia preferred upon request", true),
                BirthPreference("p2", "Gestion de la Douleur", "Pain Management", "Liberté de mouvement, marche et utilisation du ballon de Klein", "Freedom to move, walk, and use birthing ball", true),
                BirthPreference("p3", "Gestion de la Douleur", "Pain Management", "Bain chaud ou douches délassantes durant le travail", "Access to warm tub/shower for pain relief during labor", true),
                BirthPreference("e1", "Ambiance en Salle de Naissance", "Delivery Room Atmosphere", "Lumière tamisée et atmosphère feutrée et calme", "Dimmed soft ambient lighting and calm quiet atmosphere", true),
                BirthPreference("e2", "Ambiance en Salle de Naissance", "Delivery Room Atmosphere", "Diffusion d'une playlist musicale relaxante préparée par les parents", "Soft relaxing background music chosen by parents", true),
                BirthPreference("e3", "Ambiance en Salle de Naissance", "Delivery Room Atmosphere", "Présence continue et soutien exclusif du papa aux côtés de Fatou", "Continuous emotional and physical presence of father by Fatou's side", true),
                BirthPreference("b1", "Accouchement & Cordon", "Delivery & Umbilical Cord", "Peau à peau immédiat et ininterrompu dès la naissance (> 1 heure)", "Immediate and uninterrupted skin-to-skin contact (> 1 hour)", true),
                BirthPreference("b2", "Accouchement & Cordon", "Delivery & Umbilical Cord", "Clampage tardif du cordon ombilical jusqu'à arrêt des pulsations", "Delayed umbilical cord clamping until pulsation ceases completely", true),
                BirthPreference("b3", "Accouchement & Cordon", "Delivery & Umbilical Cord", "Papa coupe le cordon ombilical de son petit garçon", "Father to cut our baby boy's umbilical cord", true),
                BirthPreference("n1", "Premiers Soins de Bébé", "Newborn Care & Feeding", "Mise au sein précoce et tétée d'accueil dans la première heure", "Early breastfeeding initiation within the golden hour", true),
                BirthPreference("n2", "Premiers Soins de Bébé", "Newborn Care & Feeding", "Examen pédiatrique, pesée et soins non urgents différés après le peau à peau", "Newborn weight and exams deferred after initial skin-to-skin bonding", true),
                BirthPreference("n3", "Premiers Soins de Bébé", "Newborn Care & Feeding", "Aucun complément artificiel ni tétine sans l'accord express des parents", "No artificial pacifiers or formula bottles without explicit parental consent", true)
            )
        )
    }

    val categories = remember(preferences) {
        preferences.map { if (isFrench) it.categoryFr else it.categoryEn }.distinct()
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF5ED)),
        border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.35f)),
        shape = RoundedCornerShape(14.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = null,
                        tint = Color(0xFF92400E),
                        modifier = Modifier.size(22.dp)
                    )
                    Column {
                        Text(
                            text = if (isFrench) "Projet de Naissance Interactif" else "Interactive Birth Plan Builder",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E1905)
                        )
                        Text(
                            text = if (isFrench) "Vos souhaits configurables pour le jour J à la maternité" else "Your customizable delivery preferences for maternity staff",
                            fontSize = 10.sp,
                            color = Color(0xFF78350F)
                        )
                    }
                }

                Button(
                    onClick = {
                        val selectedList = preferences.filter { it.isSelected }
                        val sb = StringBuilder()
                        sb.append(if (isFrench) "PROJET DE NAISSANCE • FATOU & SON PETIT GARÇON\n" else "BIRTH PLAN • FATOU & BABY BOY\n")
                        sb.append(if (isFrench) "Date Prévue d'Accouchement (DPA) : 22 Juillet 2027\n" else "Estimated Due Date: July 22, 2027\n")
                        sb.append(if (isFrench) "Protocole : Blastocyste J5 euploïde 46, XY (RMA of New York)\n\n" else "Protocol: Day-5 euploid male 46, XY blastocyst (RMA of New York)\n\n")

                        categories.forEach { cat ->
                            sb.append("--- $cat ---\n")
                            selectedList
                                .filter { (if (isFrench) it.categoryFr else it.categoryEn) == cat }
                                .forEach { item ->
                                    sb.append("• ${if (isFrench) item.labelFr else item.labelEn}\n")
                                }
                            sb.append("\n")
                        }

                        val intent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_SUBJECT, if (isFrench) "Projet de Naissance de Fatou" else "Fatou's Birth Plan")
                            putExtra(Intent.EXTRA_TEXT, sb.toString())
                        }
                        context.startActivity(Intent.createChooser(intent, if (isFrench) "Imprimer / Partager le projet" else "Print / Share Birth Plan"))
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF92400E)),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Print,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(if (isFrench) "Imprimer / Partager" else "Print for Hospital", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }

            Text(
                text = if (isFrench)
                    "Cochez ou décochez les souhaits pour composer votre projet de naissance personnalisé, prêt à être remis aux sages-femmes et obstétriciens de garde."
                else
                    "Select or uncheck your preferences to compose your personalized birth plan, formatted and ready to present to your delivery team.",
                fontSize = 10.sp,
                color = Color(0xFF593210)
            )

            Divider(color = Color(0xFFBFA278).copy(alpha = 0.3f))

            // Categories with items
            categories.forEach { cat ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = cat,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E)
                    )
                    preferences
                        .filter { (if (isFrench) it.categoryFr else it.categoryEn) == cat }
                        .forEach { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Checkbox(
                                    checked = item.isSelected,
                                    onCheckedChange = { isChecked ->
                                        preferences = preferences.map {
                                            if (it.id == item.id) it.copy(isSelected = isChecked) else it
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF92400E)),
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = if (isFrench) item.labelFr else item.labelEn,
                                    fontSize = 11.sp,
                                    color = Color(0xFF2E1905),
                                    lineHeight = 15.sp
                                )
                            }
                        }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

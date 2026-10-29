package com.example.ui.prenatal

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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IvfMedicationTracker(currentWeek: Int, isFrench: Boolean) {
    var pioTaken by remember { mutableStateOf(true) }
    var estraceTaken by remember { mutableStateOf(true) }
    var prenatalTaken by remember { mutableStateOf(true) }
    var babyAspirinTaken by remember { mutableStateOf(true) }
    var injectionSite by remember { mutableStateOf("Fessier Droit (Gluteal Right)") }

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
            verticalArrangement = Arrangement.spacedBy(10.dp)
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
                        imageVector = Icons.Default.Medication,
                        contentDescription = null,
                        tint = Color(0xFF92400E),
                        modifier = Modifier.size(22.dp)
                    )
                    Column {
                        Text(
                            text = if (isFrench) "Suivi Protocole RMA & Progestérone (PIO)" else "IVF Medication & Progesterone (PIO) Tracker",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E1905)
                        )
                        Text(
                            text = if (isFrench) "Relais placentaire à la Semaine 10" else "Placental handover at Week 10",
                            fontSize = 10.sp,
                            color = Color(0xFF78350F)
                        )
                    }
                }

                Surface(
                    color = if (currentWeek <= 10) Color(0xFF16A34A).copy(alpha = 0.15f) else Color(0xFF78350F).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (currentWeek <= 10)
                            (if (isFrench) "Protocole Actif (S$currentWeek)" else "Active Protocol (W$currentWeek)")
                        else
                            (if (isFrench) "Sevrage Effectué ✓" else "Weaned Off ✓"),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = if (currentWeek <= 10) Color(0xFF15803D) else Color(0xFF78350F),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Text(
                text = if (isFrench)
                    "Protocole RMA of New York pour transfert blastocyste J5 : maintien d'un taux sérique de progestérone > 20 ng/mL jusqu'à la transition placentaire complète."
                else
                    "RMA of New York Day-5 FET protocol: maintaining serum progesterone > 20 ng/mL until complete placental hormone transition at 10 weeks.",
                fontSize = 10.sp,
                color = Color(0xFF593210),
                fontStyle = FontStyle.Italic
            )

            Divider(color = Color(0xFFBFA278).copy(alpha = 0.3f))

            // Medication Check-off List for Today
            Text(
                text = if (isFrench) "Prises du jour" else "Today's Prescriptions",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF78350F)
            )

            MedicationRow(
                name = if (isFrench) "Progestérone Retard (PIO) 50 mg / 1 mL IM" else "Progesterone in Oil (PIO) 50 mg / 1 mL IM",
                timing = if (isFrench) "1x / jour le soir (horaire fixe)" else "1x / day evening (fixed schedule)",
                checked = pioTaken,
                onCheckedChange = { pioTaken = it }
            )

            MedicationRow(
                name = if (isFrench) "Estrace (Estradiol) 2 mg oral" else "Estrace (Estradiol) 2 mg oral",
                timing = if (isFrench) "2x / jour avec un repas" else "2x / day with meal",
                checked = estraceTaken,
                onCheckedChange = { estraceTaken = it }
            )

            MedicationRow(
                name = if (isFrench) "Vitamines Prénatales & Acide Folique 800 mcg" else "Prenatal Multivitamin & Folate 800 mcg",
                timing = if (isFrench) "1x / jour le matin" else "1x / day morning",
                checked = prenatalTaken,
                onCheckedChange = { prenatalTaken = it }
            )

            MedicationRow(
                name = if (isFrench) "Aspirine faible dose (Kardegic / Baby Aspirin 81 mg)" else "Low-Dose Baby Aspirin 81 mg",
                timing = if (isFrench) "1x / jour au déjeuner" else "1x / day with lunch",
                checked = babyAspirinTaken,
                onCheckedChange = { babyAspirinTaken = it }
            )

            // Injection site rotation
            Surface(
                color = Color.White,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color(0xFFBFA278).copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = if (isFrench) "Rotation du site d'injection IM (PIO) :" else "IM Injection Site Rotation (PIO):",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF78350F)
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        FilterChip(
                            selected = injectionSite.contains("Droit"),
                            onClick = { injectionSite = "Fessier Droit (Gluteal Right)" },
                            label = { Text(if (isFrench) "👉 Fessier Droit" else "👉 Right Gluteal", fontSize = 10.sp) }
                        )
                        FilterChip(
                            selected = injectionSite.contains("Gauche"),
                            onClick = { injectionSite = "Fessier Gauche (Gluteal Left)" },
                            label = { Text(if (isFrench) "👈 Fessier Gauche" else "👈 Left Gluteal", fontSize = 10.sp) }
                        )
                    }
                    Text(
                        text = if (isFrench)
                            "Conseil RMA : Chauffer légèrement la fiole avant injection et appliquer une compresse chaude 5 min après pour favoriser la diffusion."
                        else
                            "RMA Tip: Warm the oil vial slightly between hands before injection; apply warm compress for 5 min post-injection to soothe.",
                        fontSize = 9.sp,
                        color = Color(0xFF78350F)
                    )
                }
            }
        }
    }
}

@Composable
private fun MedicationRow(
    name: String,
    timing: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(name, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E1905))
            Text(timing, fontSize = 9.sp, color = Color(0xFF78350F))
        }
        Checkbox(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF92400E))
        )
    }
}

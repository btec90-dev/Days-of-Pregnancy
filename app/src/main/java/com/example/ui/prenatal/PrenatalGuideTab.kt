package com.example.ui.prenatal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrenatalGuideTab(
    isFrench: Boolean,
    onOpenGlossary: (() -> Unit)? = null
) {
    var section1Expanded by remember { mutableStateOf(true) }
    var section2Expanded by remember { mutableStateOf(false) }
    var section3Expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Hero Header Banner (Compact)
        Surface(
            color = Color(0xFF92400E).copy(alpha = 0.08f),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color(0xFFBFA278)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = Color(0xFF92400E),
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = if (isFrench) "Guide & Précautions • Bébé Garçon" else "Guide & Precautions • Baby Boy",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E1905)
                    )
                }
                Text(
                    text = if (isFrench)
                        "Conseils bienveillants et repères cliniques essentiels pour votre parcours RMA."
                    else
                        "Caring advice and essential clinical milestones for your RMA journey.",
                    fontSize = 10.sp,
                    color = Color(0xFF593210)
                )
            }
        }

        // Quick Medical Glossary Spotlight Banner
        Surface(
            color = Color(0xFFFAF4E8),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color(0xFF92400E)),
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOpenGlossary?.invoke() }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(text = "📚", fontSize = 20.sp)
                    Column {
                        Text(
                            text = if (isFrench) "Glossaire Médical (FET, hCG, DPNI...)" else "Medical Glossary (FET, hCG, NIPT...)",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E1905)
                        )
                        Text(
                            text = if (isFrench) "Définitions et questions pour votre médecin" else "Definitions and questions for your doctor",
                            fontSize = 10.sp,
                            color = Color(0xFF78350F)
                        )
                    }
                }
                if (onOpenGlossary != null) {
                    Button(
                        onClick = onOpenGlossary,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF92400E)),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                        modifier = Modifier.height(28.dp)
                    ) {
                        Text(
                            text = if (isFrench) "Ouvrir" else "Open",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Section 1: Journaling Framework (Collapsible Card)
        CollapsibleGuideCard(
            title = if (isFrench) "Cadre de Rédaction & Suivi Clinique" else "Journaling & Clinical Framework",
            icon = Icons.Default.EditNote,
            headerColor = Color(0xFF92400E),
            containerColor = Color(0xFFFAF5ED),
            borderStroke = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.35f)),
            isExpanded = section1Expanded,
            onToggle = { section1Expanded = !section1Expanded }
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                FrameworkItem(
                    title = if (isFrench) "🔬 Biomarqueurs & Échographies" else "🔬 Lab Values & Ultrasounds",
                    desc = if (isFrench) "Consignez la cinétique Bêta-hCG, clarté nucale (S12) et rythme cardiaque." else "Log serial Beta-hCG, nuchal translucency (W12) and fetal heart rate."
                )
                FrameworkItem(
                    title = if (isFrench) "💌 Lettres Intimes à Bébé" else "💌 Intimate Letters to Baby",
                    desc = if (isFrench) "Exprimez vos espoirs après le transfert blastocyste J5." else "Express your hopes after the Day-5 blastocyst transfer."
                )
                FrameworkItem(
                    title = if (isFrench) "🩺 Questions pour le Médecin" else "🩺 Doctor Consultation Questions",
                    desc = if (isFrench) "Notez les posologies PIO et doutes du quotidien." else "Note PIO medication schedules and daily questions."
                )
            }
        }

        // Section 2: Things to Avoid (Collapsible Card)
        CollapsibleGuideCard(
            title = if (isFrench) "Précautions Vitales & À Éviter" else "Crucial Precautions & Things to Avoid",
            icon = Icons.Default.WarningAmber,
            headerColor = Color(0xFFB91C1C),
            containerColor = Color(0xFFFEF2F2),
            borderStroke = BorderStroke(1.dp, Color(0xFFDC2626).copy(alpha = 0.3f)),
            isExpanded = section2Expanded,
            onToggle = { section2Expanded = !section2Expanded }
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                WarningCategory(
                    title = if (isFrench) "🥗 Sécurité Alimentaire" else "🥗 Food Safety",
                    items = if (isFrench) listOf(
                        "Lait cru, fromages non pasteurisés et charcuteries crues.",
                        "Viandes crues ou saignantes (> 70°C requis).",
                        "Poissons riches en mercure et fruits de mer crus."
                    ) else listOf(
                        "Unpasteurized soft cheeses and raw deli meats.",
                        "Undercooked meats (must be well-done > 70°C).",
                        "High-mercury fish and raw sushi."
                    )
                )
                WarningCategory(
                    title = if (isFrench) "💄 Cosmétiques & Médicaments" else "💄 Skincare & OTC Drugs",
                    items = if (isFrench) listOf(
                        "Rétinoïdes, acide salicylique fort.",
                        "AINS (Ibuprofène) et aspirine antalgique."
                    ) else listOf(
                        "Retinoids and strong chemical peels.",
                        "NSAIDs (Ibuprofen) and high-dose aspirin."
                    )
                )
            }
        }

        // Section 3: Nesting Guide (Collapsible Card)
        CollapsibleGuideCard(
            title = if (isFrench) "Guide de Nidification & Préparation" else "Nesting & Baby Planning Guide",
            icon = Icons.Default.Checklist,
            headerColor = Color(0xFF15803D),
            containerColor = Color(0xFFF0FDF4),
            borderStroke = BorderStroke(1.dp, Color(0xFF16A34A).copy(alpha = 0.35f)),
            isExpanded = section3Expanded,
            onToggle = { section3Expanded = !section3Expanded }
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                NestingTrimesterCard(
                    title = if (isFrench) "1er Trimestre (S1-S13)" else "1st Trimester (W1-W13)",
                    items = if (isFrench) listOf(
                        "Déclaration de grossesse & écho T1.",
                        "Dossier PMA RMA of New York."
                    ) else listOf(
                        "Pregnancy registration & T1 scan.",
                        "RMA of New York IVF documentation."
                    )
                )
                NestingTrimesterCard(
                    title = if (isFrench) "2e & 3e Trimestres (S14-S40)" else "2nd & 3rd Trimesters (W14-W40)",
                    items = if (isFrench) listOf(
                        "Chambre de bébé (peinture sans COV).",
                        "Valise de maternité & siège-auto i-Size."
                    ) else listOf(
                        "Nursery prep (non-toxic paint).",
                        "Hospital bag & i-Size car seat."
                    )
                )
            }
        }
    }
}

@Composable
private fun CollapsibleGuideCard(
    title: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    headerColor: Color,
    containerColor: Color,
    borderStroke: BorderStroke,
    isExpanded: Boolean,
    onToggle: () -> Unit,
    content: @Composable () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = borderStroke,
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onToggle() },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = headerColor,
                        modifier = Modifier.size(18.dp)
                    )
                    Text(
                        text = title,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = headerColor
                    )
                }
                Icon(
                    imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = headerColor,
                    modifier = Modifier.size(20.dp)
                )
            }

            if (isExpanded) {
                Divider(color = headerColor.copy(alpha = 0.2f))
                Spacer(modifier = Modifier.height(2.dp))
                content()
            }
        }
    }
}

@Composable
private fun FrameworkItem(title: String, desc: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF78350F))
        Text(desc, fontSize = 10.sp, color = Color(0xFF3B2814), lineHeight = 14.sp)
    }
}

@Composable
private fun WarningCategory(title: String, items: List<String>) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF991B1B))
        items.forEach { item ->
            Text("• $item", fontSize = 10.sp, color = Color(0xFF450A0A), lineHeight = 13.sp)
        }
    }
}

@Composable
private fun NestingTrimesterCard(title: String, items: List<String>) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF166534))
        items.forEach { item ->
            var checked by remember { mutableStateOf(false) }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Checkbox(
                    checked = checked,
                    onCheckedChange = { checked = it },
                    colors = CheckboxDefaults.colors(checkedColor = Color(0xFF15803D)),
                    modifier = Modifier.size(18.dp)
                )
                Text(item, fontSize = 10.sp, color = Color(0xFF14532D))
            }
        }
    }
}

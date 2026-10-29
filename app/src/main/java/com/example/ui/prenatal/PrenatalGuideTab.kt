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
fun PrenatalGuideTab(
    isFrench: Boolean,
    onOpenGlossary: (() -> Unit)? = null
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Banner
        Surface(
            color = Color(0xFF92400E).copy(alpha = 0.08f),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color(0xFFBFA278)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.MenuBook,
                        contentDescription = null,
                        tint = Color(0xFF92400E),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = if (isFrench)
                            "Conseils d'Écriture, Précautions & Préparatifs avant l'Arrivée"
                        else
                            "Writing Guidance, Precautions & Nesting Guide Before Arrival",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E1905)
                    )
                }
                Text(
                    text = if (isFrench)
                        "Des repères bienveillants et scientifiquement vérifiés pour vivre chaque étape du parcours avec sérénité : de la rédaction de vos souvenirs aux précautions quotidiennes et à l'organisation de la chambre de votre petit garçon."
                    else
                        "Caring, evidence-based guidance to experience every milestone of your journey with peace of mind: from capturing memories to daily safety precautions and nursery preparations for your baby boy.",
                    fontSize = 11.sp,
                    color = Color(0xFF593210),
                    lineHeight = 16.sp
                )
            }
        }

        // Quick Medical Glossary Spotlight Banner
        Surface(
            color = Color(0xFFFAF4E8),
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, Color(0xFF92400E)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = "📚", fontSize = 24.sp)
                    Column {
                        Text(
                            text = if (isFrench) "Glossaire Médical (FET, hCG, DPNI...)" else "Medical Glossary (FET, hCG, NIPT...)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E1905)
                        )
                        Text(
                            text = if (isFrench) "Définitions claires, repères cliniques et questions prêtes pour votre médecin"
                            else "Clear definitions, clinical milestones, and copyable questions for your doctor",
                            fontSize = 10.sp,
                            color = Color(0xFF78350F)
                        )
                    }
                }
                if (onOpenGlossary != null) {
                    Button(
                        onClick = onOpenGlossary,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF92400E)),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = if (isFrench) "Consulter" else "Explore",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }

        // Section 1: Journaling & Note-Taking Framework
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF5ED)),
            border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.35f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.EditNote,
                        contentDescription = null,
                        tint = Color(0xFF92400E),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = if (isFrench) "Cadre de Rédaction & Suivi Clinique Intime" else "Journaling & Clinical Tracking Framework",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF92400E)
                    )
                }
                Divider(color = Color(0xFFBFA278).copy(alpha = 0.3f))

                FrameworkItem(
                    title = if (isFrench) "🔬 Suivi des Biomarqueurs & Échographies" else "🔬 Lab Values & Ultrasound Telemetry",
                    desc = if (isFrench)
                        "Consignez précisément chaque cinétique Bêta-hCG (J9, J11, J14), la mesure de la clarté nucale (S12) et le rythme cardiaque fœtal (120-160 bpm) pour objectiver la progression sereine de votre petit prince."
                    else
                        "Log your serial Beta-hCG levels (D9, D11, D14), nuchal translucency biometrics (W12), and fetal heart rates (120-160 bpm) to follow your baby boy's steady clinical milestones."
                )

                FrameworkItem(
                    title = if (isFrench) "💌 Lettres Intimes à Bébé Garçon" else "💌 Intimate Letters to Your Baby Boy",
                    desc = if (isFrench)
                        "Écrivez vos pensées du cœur, vos espoirs après le transfert blastocyste J5, et racontez-lui comment son papa et vous l'attendiez avec tant d'amour à chaque étape de ce protocole RMA."
                    else
                        "Write personal messages, your hopes after the Day-5 blastocyst transfer, and tell him how deeply you and his father cherished each step of this RMA journey."
                )

                FrameworkItem(
                    title = if (isFrench) "🩺 Préparation des Questions Médicales" else "🩺 Doctor Appointment Question Lists",
                    desc = if (isFrench)
                        "Notez vos doutes dès qu'ils surviennent : posologie de la progestérone (PIO), sensations ligamentaires, supplémentation en fer ou organisation de l'accouchement."
                    else
                        "Record your questions as they arise: progesterone (PIO) schedule, ligament aches, prenatal iron, and delivery logistics for your next clinical consultation."
                )

                FrameworkItem(
                    title = if (isFrench) "🌱 Écoute Émotionnelle du Parcours FIV" else "🌱 Processing IVF Emotional Milestones",
                    desc = if (isFrench)
                        "Honorez la résilience de votre corps. Reconnaître les peurs passagères et célébrer chaque étape franchie (battement cardiaque, DPNI, viabilité) renforce votre lien maternel."
                    else
                        "Honor your body's resilience. Acknowledging emotional peaks and celebrating each hurdle (first heartbeat, NIPT, viability) deepens your maternal bond."
                )
            }
        }

        // Section 2: Crucial "Things to Avoid"
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
            border = BorderStroke(1.dp, Color(0xFFDC2626).copy(alpha = 0.3f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.WarningAmber,
                        contentDescription = null,
                        tint = Color(0xFFB91C1C),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = if (isFrench) "Précautions Vitales & Choses à Éviter" else "Crucial 'Things to Avoid' & Safety Rules",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF991B1B)
                    )
                }
                Divider(color = Color(0xFFFCA5A5))

                WarningCategory(
                    title = if (isFrench) "🥗 Sécurité Alimentaire (Listériose & Toxoplasmose)" else "🥗 Food Safety (Listeria & Toxoplasmosis)",
                    items = if (isFrench) listOf(
                        "Fromages au lait cru non pasteurisés et croûtes lavées.",
                        "Charcuteries crues (jambon cru, saucisson, rillettes, pâtés).",
                        "Viandes crues ou saignantes (cuisson à cœur requise > 70°C).",
                        "Poissons riches en mercure (thon rouge, espadon, requin) et fruits de mer crus.",
                        "Fruits et légumes non lavés ou souillés de terre (toxoplasmose)."
                    ) else listOf(
                        "Unpasteurized soft cheeses and raw milk dairy.",
                        "Cold cuts and unheated deli meats (listeria risk).",
                        "Raw or undercooked meats (must be cooked well-done > 70°C).",
                        "High-mercury fish (swordfish, king mackerel, bigeye tuna) and raw sushi.",
                        "Unwashed fruits and vegetables exposed to garden soil."
                    )
                )

                WarningCategory(
                    title = if (isFrench) "💄 Cosmétiques & Soins Dermatologiques" else "💄 Skincare Exclusions",
                    items = if (isFrench) listOf(
                        "Rétinoïdes et dérivés de vitamine A (Rétinol, Trétinoïne, Accutane) — formellement tératogènes.",
                        "Hydroquinone (agents éclaircissants dermiques).",
                        "Acide salicylique à forte concentration (> 2% en peeling).",
                        "Filtres solaires chimiques perturbateurs (préférer les écrans minéraux zinc/dioxyde de titane)."
                    ) else listOf(
                        "Retinoids and Vitamin A derivatives (Retinol, Tretinoin) — strictly teratogenic.",
                        "Hydroquinone and skin-bleaching agents.",
                        "High-strength chemical salicylic acid peels (>2%).",
                        "Chemical sunscreens with oxybenzone (prefer mineral zinc oxide formulas)."
                    )
                )

                WarningCategory(
                    title = if (isFrench) "💊 Médicaments en Vente Libre Proscrits" else "💊 Forbidden OTC Medications",
                    items = if (isFrench) listOf(
                        "Anti-inflammatoires non stéroïdiens (AINS) : Ibuprofène (Advil, Nurofen), Kétoprofène — risque rénal et cardio-fœtal grave.",
                        "Aspirine à dose antalgique (sauf Kardegic 100mg prescrit par RMA).",
                        "Décongestionnants nasaux oraux contenant de la pseudoéphédrine.",
                        "Huiles essentielles pures sans avis médical obstétrical."
                    ) else listOf(
                        "NSAIDs: Ibuprofen (Advil, Motrin), Naproxen (Aleve) — severe fetal renal and cardiac risks.",
                        "High-dose Aspirin (except baby aspirin 81mg prescribed by RMA protocol).",
                        "Oral nasal decongestants containing pseudoephedrine.",
                        "Undiluted essential oils without maternal-fetal medicine approval."
                    )
                )

                WarningCategory(
                    title = if (isFrench) "🌡️ Environnement & Hygiène de Vie" else "🌡️ Environmental & Physical Hazards",
                    items = if (isFrench) listOf(
                        "Bains brûlants, saunas et jacuzzis (> 38.5°C) — risque d'hyperthermie au T1.",
                        "Nettoyage de litière pour chat sans gants (réservoir toxoplasmose).",
                        "Caféine excessive (> 200 mg/jour, soit plus d'un café espresso).",
                        "Exposition à des solvants, peintures à l'huile et pesticides ménagers."
                    ) else listOf(
                        "Hot tubs, saunas, and hot baths (> 38.5°C) — hyperthermia risk in T1.",
                        "Changing cat litter boxes without gloves (toxoplasmosis hazard).",
                        "Excess caffeine (> 200 mg/day, more than 1 strong espresso cup).",
                        "Inhaling toxic paint fumes, chemical solvents, or heavy pesticides."
                    )
                )
            }
        }

        // Section 3: Comprehensive Baby Planning & Nesting Guide
        Card(
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF0FDF4)),
            border = BorderStroke(1.dp, Color(0xFF16A34A).copy(alpha = 0.35f)),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Checklist,
                        contentDescription = null,
                        tint = Color(0xFF15803D),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = if (isFrench) "Guide de Préparation & Nidification (Nesting)" else "Baby Planning & Nesting Guide by Trimester",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF15803D)
                    )
                }
                Divider(color = Color(0xFF86EFAC))

                NestingTrimesterCard(
                    title = if (isFrench) "Premier Trimestre (S1 - S13)" else "First Trimester (W1 - W13)",
                    items = if (isFrench) listOf(
                        "Choix de la maternité et réservation précoce.",
                        "Confirmation échographique T1 et déclaration de grossesse.",
                        "Constitution du dossier médical PMA (RMA of New York).",
                        "Achat d'un coussin de grossesse ergonomique pour le sommeil."
                    ) else listOf(
                        "Hospital / Birthing center research and pre-registration.",
                        "First trimester nuchal translucency scan and prenatal records setup.",
                        "Compiling RMA of New York embryo transfer and IVF documentation.",
                        "Ergonomic pregnancy pillow for optimal maternal rest."
                    )
                )

                NestingTrimesterCard(
                    title = if (isFrench) "Deuxième Trimestre (S14 - S27)" else "Second Trimester (W14 - W27)",
                    items = if (isFrench) listOf(
                        "Aménagement de la chambre de votre petit garçon (peinture sans COV).",
                        "Création et partage de la liste de naissance.",
                        "Inscription aux cours de préparation à l'accouchement et allaitement.",
                        "Recherche et prise de contact avec le pédiatre référent.",
                        "Choix du lit à barreaux et du matelas respirant certifié Oeko-Tex."
                    ) else listOf(
                        "Designing baby boy's nursery (zero-VOC non-toxic paint).",
                        "Curating baby registry with newborn essentials.",
                        "Registering for childbirth education and breastfeeding classes.",
                        "Interviewing and selecting baby boy's future pediatrician.",
                        "Selecting breathable Oeko-Tex certified crib mattress and bedding."
                    )
                )

                NestingTrimesterCard(
                    title = if (isFrench) "Troisième Trimestre (S28 - S40)" else "Third Trimester (W28 - W40)",
                    items = if (isFrench) listOf(
                        "Installation et vérification du siège-auto bébé (Base ISOFIX homologuée i-Size).",
                        "Bouclage de la valise de maternité pour maman et bébé garçon (S36).",
                        "Lavage de tous les premiers vêtements de bébé avec une lessive hypoallergénique.",
                        "Préparation des repas d'avance (Batch-cooking & congélation pour le 4e trimestre).",
                        "Finalisation et impression du projet de naissance pour l'équipe soignante."
                    ) else listOf(
                        "Installing and testing infant car seat (i-Size compliant ISOFIX base).",
                        "Packing hospital bags for mother and baby boy (ready by Week 36).",
                        "Washing newborn clothes and swaddles with hypoallergenic fragrance-free detergent.",
                        "Batch cooking & freezing nourishing meals for postpartum recovery.",
                        "Finalizing and printing birth plan preferences for labor & delivery staff."
                    )
                )
            }
        }
    }
}

@Composable
private fun FrameworkItem(title: String, desc: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF78350F))
        Text(desc, fontSize = 11.sp, color = Color(0xFF3B2814), lineHeight = 15.sp)
    }
}

@Composable
private fun WarningCategory(title: String, items: List<String>) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(2.dp)) {
        Text(title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF991B1B))
        items.forEach { item ->
            Text("• $item", fontSize = 10.sp, color = Color(0xFF450A0A), lineHeight = 14.sp)
        }
    }
}

@Composable
private fun NestingTrimesterCard(title: String, items: List<String>) {
    var isExpanded by remember { mutableStateOf(true) }
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(3.dp)) {
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
                    modifier = Modifier.size(20.dp)
                )
                Text(item, fontSize = 10.sp, color = Color(0xFF14532D))
            }
        }
    }
}

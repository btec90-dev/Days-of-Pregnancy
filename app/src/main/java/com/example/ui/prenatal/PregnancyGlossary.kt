package com.example.ui.prenatal

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class GlossaryCategory(
    val titleFr: String,
    val titleEn: String,
    val iconName: String
) {
    ALL("Tous", "All", "⭐"),
    IVF("PMA & Embryologie", "IVF & Embryology", "🔬"),
    LABS("Analyses & Hormones", "Labs & Hormones", "🧪"),
    ULTRASOUND("Échographie & Biométrie", "Ultrasound & Biometrics", "🩺"),
    OBGYN("Obstétrique & Santé", "Obstetrics & Health", "🩺"),
    DELIVERY("Accouchement & Post-partum", "Labor & Postpartum", "👶")
}

data class GlossaryItem(
    val id: String,
    val acronym: String,
    val fullNameFr: String,
    val fullNameEn: String,
    val category: GlossaryCategory,
    val definitionFr: String,
    val definitionEn: String,
    val clinicalContextFr: String,
    val clinicalContextEn: String,
    val doctorQuestionFr: String,
    val doctorQuestionEn: String
)

val GLOSSARY_ITEMS = listOf(
    GlossaryItem(
        id = "fet",
        acronym = "FET",
        fullNameFr = "Frozen Embryo Transfer (Transfert d'Embryon Congelé)",
        fullNameEn = "Frozen Embryo Transfer",
        category = GlossaryCategory.IVF,
        definitionFr = "Procédure PMA où un embryon préalablement congelé (dans votre cas un blastocyste J5 euploïde mâle) est décongelé puis déposé délicatement dans la cavité utérine préparée.",
        definitionEn = "Assisted reproductive procedure where a previously cryopreserved embryo (in your case, a Day-5 euploid male blastocyst) is thawed and gently transferred into a prepared uterine cavity.",
        clinicalContextFr = "La DPA se calcule exactement comme : Date du transfert FET - 19 jours pour simuler la DDR théorique. Le soutien en progestérone (PIO) est indispensable jusqu'à ce que le placenta prenne le relais.",
        clinicalContextEn = "EDD is calculated as: FET Date - 19 days to establish medical LMP. Progesterone supplementation (PIO) is critical until the placenta fully assumes hormone synthesis around week 10-12.",
        doctorQuestionFr = "Quelle est l'épaisseur optimale de mon endomètre et mes taux sanguins de progestérone avant le transfert ?",
        doctorQuestionEn = "What were my endometrial lining thickness and serum progesterone levels prior to embryo transfer?"
    ),
    GlossaryItem(
        id = "beta_hcg",
        acronym = "beta-hCG",
        fullNameFr = "Hormone Chorionique Gonadotrope Humaine (Fraction Bêta)",
        fullNameEn = "Human Chorionic Gonadotropin (Beta subunit)",
        category = GlossaryCategory.LABS,
        definitionFr = "Hormone sécrétée par les cellules du trophoblaste (futur placenta) dès l'implantation de l'embryon dans l'endomètre. C'est le marqueur biologique de la grossesse.",
        definitionEn = "Hormone produced by trophoblastic cells (future placenta) upon embryo implantation in the uterine wall. It serves as the definitive clinical biomarker of pregnancy.",
        clinicalContextFr = "En début de grossesse (jusqu'à 6 000 mUI/mL), le taux de beta-hCG double approximativement toutes les 48 à 72 heures. Une valeur > 5 mUI/mL confirme l'implantation.",
        clinicalContextEn = "In early gestation (up to 6,000 mIU/mL), beta-hCG levels typically double every 48 to 72 hours. Values above 5 mIU/mL confirm positive embryonic implantation.",
        doctorQuestionFr = "Mon temps de doublement de beta-hCG sur 48h est-il rassurant et quand programmons-nous la première échographie du sac gestationnel ?",
        doctorQuestionEn = "Is my 48-hour beta-hCG doubling time within expected parameters, and when should we schedule the first gestational sac ultrasound?"
    ),
    GlossaryItem(
        id = "nipt",
        acronym = "NIPT / DPNI",
        fullNameFr = "Dépistage Prénatal Non Invasif (ADN fœtal libre circulant)",
        fullNameEn = "Non-Invasive Prenatal Testing (Cell-Free Fetal DNA)",
        category = GlossaryCategory.LABS,
        definitionFr = "Prise de sang maternelle réalisée dès la 10e semaine de grossesse pour analyser les fragments d'ADN fœtal circulant dans le sang maternel et dépister les aneuploïdies chromosomiques.",
        definitionEn = "A maternal blood draw performed from week 10 onwards that isolates cell-free fetal DNA to screen for common chromosomal aneuploidies with over 99% accuracy.",
        clinicalContextFr = "Dépiste avec une grande sensibilité la Trisomie 21 (Down), 18 (Edwards), 13 (Patau) et confirme le sexe chromosomique (XY pour votre petit garçon).",
        clinicalContextEn = "Screens with high sensitivity for Trisomy 21 (Down), Trisomy 18 (Edwards), Trisomy 13 (Patau) and confirms sex chromosomes (XY for your baby boy).",
        doctorQuestionFr = "Quelle est la fraction fœtale obtenue sur mon prélèvement sanguin DPNI (doit être > 4%) ?",
        doctorQuestionEn = "What was the fetal fraction reported on my NIPT blood draw (ideally > 4%)?"
    ),
    GlossaryItem(
        id = "pgt_a",
        acronym = "PGT-A",
        fullNameFr = "Test Génétique Préimplantatoire pour Aneuploïdies",
        fullNameEn = "Preimplantation Genetic Testing for Aneuploidies",
        category = GlossaryCategory.IVF,
        definitionFr = "Analyse génétique des cellules du trophectoderme d'un blastocyste au laboratoire PMA pour vérifier que l'embryon possède bien 46 chromosomes normaux (euploïde).",
        definitionEn = "Genetic biopsy of trophectoderm cells on a day 5-6 blastocyst in the IVF lab to ensure 46 normal chromosomes (euploid status) prior to freezing.",
        clinicalContextFr = "Votre embryon transféré est un embryon euploïde mâle de Jour 5, ce qui réduit drastiquement le risque de fausse couche d'origine chromosomique.",
        clinicalContextEn = "Your transferred embryo is a verified Day-5 euploid male, dramatically lowering the risk of miscarriage attributable to chromosomal aneuploidy.",
        doctorQuestionFr = "Quel était le grade morphologique Gardner de mon embryon J5 euploïde (ex: 4AA, 5AA) ?",
        doctorQuestionEn = "What was the exact Gardner morphological grade of my Day-5 euploid embryo (e.g., 4AA, 5AA)?"
    ),
    GlossaryItem(
        id = "pio",
        acronym = "PIO",
        fullNameFr = "Progestérone en Huile (Progesterone in Oil)",
        fullNameEn = "Progesterone in Oil",
        category = GlossaryCategory.IVF,
        definitionFr = "Forme injectable intramusculaire de progestérone naturelle dissoute dans de l'huile (sésame ou éthyle oléate), injectée dans le quadrant supéro-externe de la fesse.",
        definitionEn = "Intramuscular injectable formulation of natural progesterone suspended in oil (sesame or ethyl oleate), administered deep into the upper outer gluteal quadrant.",
        clinicalContextFr = "Essentielle lors d'un cycle de transfert programmé où le corps jaune est absent. Elle maintient la decidua utérine jusqu'à la relève placentaire vers 10-12 semaines de grossesse.",
        clinicalContextEn = "Indispensable in medicated/programmed frozen embryo transfer cycles where a natural corpus luteum is absent. Supports the endometrial decidua until placental takeover around week 10-12.",
        doctorQuestionFr = "À quelle semaine exacte de grossesse pouvons-nous commencer le sevrage progressif des injections de PIO ?",
        doctorQuestionEn = "At which exact gestational week should we begin gradual weaning of my intramuscular PIO injections?"
    ),
    GlossaryItem(
        id = "nt_cn",
        acronym = "CN / NT",
        fullNameFr = "Clarté Nucale (Nuchal Translucency)",
        fullNameEn = "Nuchal Translucency",
        category = GlossaryCategory.ULTRASOUND,
        definitionFr = "Espace liquidien sous-cutané situé entre la peau et les tissus mous du cou du fœtus, mesuré lors de l'échographie du premier trimestre (entre 11 SA et 13 SA + 6 j).",
        definitionEn = "Subcutaneous fluid pocket located behind the fetal neck, precisely measured during the first-trimester ultrasound screening between 11w0d and 13w6d.",
        clinicalContextFr = "Une épaisseur normale est généralement inférieure à 2.5 - 3.0 mm (selon la LCC). Une valeur fine est un marqueur très rassurant associé à votre DPNI.",
        clinicalContextEn = "A standard measurement is generally under 2.5 - 3.0 mm depending on fetal crown-rump length (CRL). A normal thickness is a reassuring indicator combined with NIPT.",
        doctorQuestionFr = "Quelle est la mesure précise en millimètres de la clarté nucale et correspond-elle au 50e percentile pour son terme ?",
        doctorQuestionEn = "What was the exact millimeter measurement of the nuchal translucency, and does it align with the 50th percentile for gestational age?"
    ),
    GlossaryItem(
        id = "fhr_bcf",
        acronym = "BCF / FHR",
        fullNameFr = "Bruits du Cœur Fœtal (Fetal Heart Rate)",
        fullNameEn = "Fetal Heart Rate",
        category = GlossaryCategory.OBGYN,
        definitionFr = "Rythme cardiaque du fœtus mesuré en battements par minute (bpm) à l'aide d'une sonde Doppler ou lors de l'échographie obstétricale.",
        definitionEn = "Fetal cardiac rhythm quantified in beats per minute (bpm) via obstetrical ultrasound or clinical acoustic Doppler probe.",
        clinicalContextFr = "À 6 SA, le rythme débute vers 100-115 bpm, monte vers 160-180 bpm vers 9 SA, puis se stabilise entre 120 et 160 bpm pour le reste de la grossesse.",
        clinicalContextEn = "Begins around 100-115 bpm at week 6, peaks near 160-180 bpm around week 9, then stabilizes within the normal 120 to 160 bpm range for the second and third trimesters.",
        doctorQuestionFr = "Le rythme cardiaque actuel de mon bébé se situe-t-il bien dans la plage physiologique optimale de 120-160 bpm ?",
        doctorQuestionEn = "Is baby's current cardiac rhythm comfortably within the optimal physiological window of 120-160 bpm?"
    ),
    GlossaryItem(
        id = "crl_lcc",
        acronym = "LCC / CRL",
        fullNameFr = "Longueur Cranio-Caudale (Crown-Rump Length)",
        fullNameEn = "Crown-Rump Length",
        category = GlossaryCategory.ULTRASOUND,
        definitionFr = "Mesure de la longueur du fœtus depuis le sommet de la tête (vertex) jusqu'au bas des fesses, excluant les membres inférieurs.",
        definitionEn = "Measurement of the fetal length from the top of the head (crown) to the bottom of the buttocks (rump), excluding limbs.",
        clinicalContextFr = "C'est la mesure biométrique la plus précise pour dater le terme au premier trimestre (précision à ± 3-5 jours).",
        clinicalContextEn = "The most accurate ultrasound biometric parameter for pregnancy dating in the first trimester (accurate within ± 3-5 days).",
        doctorQuestionFr = "La LCC mesurée aujourd'hui correspond-elle fidèlement à l'âge gestationnel calculé depuis le transfert FET J5 ?",
        doctorQuestionEn = "Does today's measured CRL accurately match the expected gestational age calculated from our Day-5 FET transfer?"
    ),
    GlossaryItem(
        id = "bpd_bip",
        acronym = "BIP / BPD",
        fullNameFr = "Diamètre Bipariétal (Biparietal Diameter)",
        fullNameEn = "Biparietal Diameter",
        category = GlossaryCategory.ULTRASOUND,
        definitionFr = "Distance transversale entre les deux os pariétaux du crâne fœtal, mesurée au niveau des têtes des thalami et du cavum du septum pellucidum.",
        definitionEn = "Transverse distance between the two parietal bones of the fetal cranium, measured on an axial plane traversing the thalami and cavum septi pellucidi.",
        clinicalContextFr = "Mesuré lors des échographies T2 (morphologique à 22 SA) et T3 pour surveiller la croissance crânienne et le développement neurologique.",
        clinicalContextEn = "Measured during second-trimester (20-22 weeks anatomy scan) and third-trimester ultrasound evaluations to track cranial and neurological growth.",
        doctorQuestionFr = "Le diamètre crânien et le périmètre céphalique (PC) évoluent-ils harmonieusement le long de leur couloir de percentile ?",
        doctorQuestionEn = "Are the biparietal diameter and head circumference (HC) tracking harmoniously along their normal percentile curves?"
    ),
    GlossaryItem(
        id = "ac_pa",
        acronym = "PA / AC",
        fullNameFr = "Périmètre Abdominal (Abdominal Circumference)",
        fullNameEn = "Abdominal Circumference",
        category = GlossaryCategory.ULTRASOUND,
        definitionFr = "Circonférence circulaire de l'abdomen fœtal mesurée au niveau de la veine ombilicale intrahépatique et de la bulle gastrique.",
        definitionEn = "Transverse circular circumference of the fetal abdomen measured at the junction of the umbilical vein, portal sinus, and fetal stomach bubble.",
        clinicalContextFr = "C'est le paramètre le plus sensible pour évaluer la nutrition fœtale, les réserves de glycogène hépatique et détecter un RCIU ou une macrosomie.",
        clinicalContextEn = "The single most sensitive biometric indicator for fetal nutritional status and liver glycogen stores, crucial for ruling out IUGR or macrosomia.",
        doctorQuestionFr = "Quel est le percentile de croissance pour le périmètre abdominal sur la courbe de Hadlock ?",
        doctorQuestionEn = "What is the percentile ranking for the abdominal circumference on the Hadlock fetal growth curve?"
    ),
    GlossaryItem(
        id = "fl_lf",
        acronym = "LF / FL",
        fullNameFr = "Longueur Fémorale (Femur Length)",
        fullNameEn = "Femur Length",
        category = GlossaryCategory.ULTRASOUND,
        definitionFr = "Mesure de la diaphyse ossifiée du fémur fœtal, du grand trochanter jusqu'au condyle fémoral (excluant les épiphyses cartilagineuses).",
        definitionEn = "Ultrasound measurement of the ossified femoral shaft from the greater trochanter to the femoral condyle, excluding epiphyseal cartilage.",
        clinicalContextFr = "Permet de vérifier la croissance longitudinale du squelette et contribue au calcul de l'estimation du poids fœtal.",
        clinicalContextEn = "Assesses longitudinal skeletal growth and serves as a core parameter in calculating estimated fetal weight (EFW).",
        doctorQuestionFr = "La longueur fémorale confirme-t-elle une croissance staturale régulière de mon petit garçon ?",
        doctorQuestionEn = "Does the femur length confirm consistent skeletal progression for our baby boy?"
    ),
    GlossaryItem(
        id = "efw_epf",
        acronym = "EPF / EFW",
        fullNameFr = "Estimation du Poids Fœtal (Estimated Fetal Weight)",
        fullNameEn = "Estimated Fetal Weight",
        category = GlossaryCategory.ULTRASOUND,
        definitionFr = "Poids estimé du bébé en grammes, calculé automatiquement par l'échographe à l'aide de formules mathématiques combinant BIP, PC, PA et LF (ex: formule de Hadlock).",
        definitionEn = "Calculated estimate of fetal weight in grams derived from multi-biometric formulas (Hadlock) combining BPD, HC, AC, and FL.",
        clinicalContextFr = "Une marge d'erreur normale de ± 10 à 15% existe. Le percentile permet d'apprécier la trajectoire de croissance individuelle.",
        clinicalContextEn = "Carries a normal clinical margin of error of ± 10 to 15%. Percentile ranking monitors overall growth trajectory over consecutive scans.",
        doctorQuestionFr = "Dans quel percentile d'estimation de poids se situe mon bébé aujourd'hui (entre le 10e et le 90e percentile) ?",
        doctorQuestionEn = "Which estimated weight percentile is baby tracking in today (ideally between the 10th and 90th percentiles)?"
    ),
    GlossaryItem(
        id = "gdm_dg",
        acronym = "DG / GDM",
        fullNameFr = "Diabète Gestationnel (Gestational Diabetes Mellitus)",
        fullNameEn = "Gestational Diabetes Mellitus",
        category = GlossaryCategory.OBGYN,
        definitionFr = "Trouble de la tolérance aux glucides conduisant à une hyperglycémie d'intensité variable, débutant ou diagnostiqué pour la première fois pendant la grossesse sous l'effet des hormones placentaires.",
        definitionEn = "Carbohydrate intolerance resulting in hyperglycemia with onset or first recognition during pregnancy due to placental lactogen and progesterone counter-regulatory hormones.",
        clinicalContextFr = "Dépisté systématiquement entre 24 et 28 SA par l'hyperglycémie provoquée par voie orale (HGPO 75g de glucose avec prélèvements à jeun, 1h et 2h).",
        clinicalContextEn = "Screened routinely between 24 and 28 weeks gestation using the 75g 2-hour Oral Glucose Tolerance Test (OGTT: fasting, 1h, and 2h blood draws).",
        doctorQuestionFr = "Quand dois-je effectuer mon test HGPO au laboratoire et quelles sont les consignes de jeûne ?",
        doctorQuestionEn = "When should I schedule my 75g glucose tolerance test and what are the pre-test fasting instructions?"
    ),
    GlossaryItem(
        id = "preeclampsia",
        acronym = "PE",
        fullNameFr = "Pré-éclampsie (Toxémie Gravidique)",
        fullNameEn = "Preeclampsia",
        category = GlossaryCategory.OBGYN,
        definitionFr = "Pathologie de la grossesse survenant après 20 SA, associant une hypertension artérielle (PAS ≥ 140 mmHg et/ou PAD ≥ 90 mmHg) et une protéinurie ou une atteinte organique maternelle.",
        definitionEn = "Multisystem pregnancy disorder developing after 20 weeks gestation characterized by newly elevated blood pressure (≥140/90 mmHg) accompanied by proteinuria or organ dysfunction.",
        clinicalContextFr = "Surveillée à chaque consultation mensuelle par la prise de tension artérielle et la bandelette urinaire (recherche d'albumine). Des maux de tête intenses ou des troubles visuels doivent alerter.",
        clinicalContextEn = "Monitored at every prenatal visit via blood pressure check and urine dipstick (proteinuria). Sudden severe headaches or visual floaters warrant prompt evaluation.",
        doctorQuestionFr = "Ma tension artérielle est-elle optimale et mes bandelettes urinaires sont-elles négatives pour les protéines ?",
        doctorQuestionEn = "Is my blood pressure in a healthy target range and is my urine dipstick completely negative for protein?"
    ),
    GlossaryItem(
        id = "gbs_sgb",
        acronym = "SGB / GBS",
        fullNameFr = "Streptocoque du Groupe B (Streptococcus Agalactiae)",
        fullNameEn = "Group B Streptococcus",
        category = GlossaryCategory.LABS,
        definitionFr = "Bactérie naturellement présente dans le microbiote intestinal ou vaginal chez environ 15 à 30% des femmes sans causer aucun symptôme.",
        definitionEn = "Bacterium normally colonizing the gastrointestinal and genital tract in 15-30% of healthy pregnant women without causing symptoms.",
        clinicalContextFr = "Dépisté systématiquement entre 35 et 37 SA par un prélèvement vaginal et rectal. Si positif, une antibiothérapie protectrice (pénicilline) est administrée pendant le travail pour protéger le nouveau-né.",
        clinicalContextEn = "Routinely swabbed (vaginal/rectal) at 35-37 weeks. If carrier positive, intravenous antibiotics (penicillin) are given during active labor to shield the newborn from neonatal transmission.",
        doctorQuestionFr = "À quelle semaine prévoyons-nous le prélèvement bactériologique SGB avant mon accouchement ?",
        doctorQuestionEn = "At which gestational week will we perform the vaginal-rectal GBS screening swab before delivery?"
    ),
    GlossaryItem(
        id = "apgar",
        acronym = "APGAR",
        fullNameFr = "Score d'APGAR (Apparence, Pouls, Grimace, Activité, Respiration)",
        fullNameEn = "APGAR Score",
        category = GlossaryCategory.DELIVERY,
        definitionFr = "Évaluation rapide et standardisée de la vitalité et de l'adaptation cardio-respiratoire du nouveau-né, notée de 0 à 10 à 1 minute et à 5 minutes de vie.",
        definitionEn = "Rapid standardized clinical score (0 to 10 points) assessing newborn extrauterine adaptation at 1 and 5 minutes after delivery across 5 signs (Appearance, Pulse, Grimace, Activity, Respiration).",
        clinicalContextFr = "Un score entre 7 et 10 à 5 minutes atteste d'une excellente transition vers la vie extra-utérine.",
        clinicalContextEn = "A score of 7 to 10 at 5 minutes indicates a smooth, reassuring transition to independent extrauterine physiology.",
        doctorQuestionFr = "Le pédiatre ou la sage-femme évalue-t-il l'APGAR directement en peau-à-peau avec maman si le score est optimal ?",
        doctorQuestionEn = "Will the pediatrician or midwife conduct the APGAR scoring while baby remains in direct skin-to-skin contact on mom's chest?"
    ),
    GlossaryItem(
        id = "dcc",
        acronym = "DCC / CCC",
        fullNameFr = "Clampage Tardif du Cordon (Delayed Cord Clamping)",
        fullNameEn = "Delayed Cord Clamping",
        category = GlossaryCategory.DELIVERY,
        definitionFr = "Pratique consistant à attendre au moins 60 secondes (ou l'arrêt des battements du cordon ombilical) avant de sectionner le cordon après la naissance.",
        definitionEn = "The evidence-based practice of waiting at least 60 seconds (or until umbilical cord pulsations cease) before clamping and cutting the cord following delivery.",
        clinicalContextFr = "Permet le transfert de 80 à 100 mL de sang placentaire riche en hémoglobine et en cellules souches, prévenant l'anémie ferriprive du nourrisson durant ses 6 premiers mois.",
        clinicalContextEn = "Facilitates placental transfusion of 80-100 mL of oxygenated, stem-cell-rich blood, substantially boosting infant iron stores for the first 6 months of life.",
        doctorQuestionFr = "L'équipe de garde de la maternité pratique-t-elle par défaut le clampage tardif du cordon de 1 à 3 minutes ?",
        doctorQuestionEn = "Does the delivery team routinely support delayed cord clamping for 1-3 minutes as standard protocol?"
    ),
    GlossaryItem(
        id = "vernix",
        acronym = "Vernix",
        fullNameFr = "Vernix Caseosa",
        fullNameEn = "Vernix Caseosa",
        category = GlossaryCategory.DELIVERY,
        definitionFr = "Substance cireuse, blanche et onctueuse sécrétée par les glandes sébacées du fœtus qui recouvre et protège sa peau dans le liquide amniotique.",
        definitionEn = "White creamy bio-barrier secreted by fetal sebaceous glands that coats and insulates the baby's delicate skin within amniotic fluid.",
        clinicalContextFr = "Forme un bouclier hydratant, antibactérien et antioxydant. Il est recommandé de ne pas donner de bain immédiat et de laisser le vernix s'absorber naturellement durant les premières 24-48 heures.",
        clinicalContextEn = "Acts as a potent antimicrobial, hydrating lipid shield. Delaying the first bath for 24-48 hours allows the vernix to absorb naturally into the newborn's skin.",
        doctorQuestionFr = "Pouvons-nous retarder le premier bain de bébé de 24 à 48 heures pour préserver son vernix protecteur ?",
        doctorQuestionEn = "Can we delay baby's first bath for 24 to 48 hours to preserve the natural protective vernix mantle?"
    ),
    GlossaryItem(
        id = "colostrum",
        acronym = "Colostrum",
        fullNameFr = "Colostrum (L'Or Jaune Maternel)",
        fullNameEn = "Colostrum (Liquid Gold)",
        category = GlossaryCategory.DELIVERY,
        definitionFr = "Le tout premier lait maternel, épais, doré et extrêmement concentré en immunoglobulines (IgA sécrétoires), protéines et leucocytes, sécrété dès la fin de grossesse et les premiers jours postpartum.",
        definitionEn = "The first maternal milk produced late in pregnancy and during the first postpartum days. Thick, golden, and densely packed with secretory IgA antibodies, proteins, and leukocytes.",
        clinicalContextFr = "Agit comme un premier vaccin naturel tapissant l'intestin du nouveau-né. Quelques gouttes suffisent pour combler l'estomac de la taille d'une bille de votre petit garçon le jour 1.",
        clinicalContextEn = "Functions as baby's first natural immunization, coating the gut lining. Just a few drops provide concentrated nourishment for baby's marble-sized stomach on Day 1.",
        doctorQuestionFr = "Puis-je pratiquer l'expression manuelle ou la mise au sein précoce dans la première heure de vie (Golden Hour) ?",
        doctorQuestionEn = "Can we initiate breastfeeding or hand-expression within the first hour of birth (the Golden Hour)?"
    ),
    GlossaryItem(
        id = "kick_counts",
        acronym = "FMC / Mouvements",
        fullNameFr = "Compte des Mouvements Fœtaux (Fetal Movement Counting)",
        fullNameEn = "Fetal Movement Counting (Kick Counts)",
        category = GlossaryCategory.OBGYN,
        definitionFr = "Technique quotidienne d'auto-surveillance de la vitalité fœtale à partir de 24-28 SA, consistant à compter le temps nécessaire pour percevoir 10 mouvements distincts de bébé.",
        definitionEn = "Daily maternal self-monitoring method from 24-28 weeks gestation tracking the time taken to feel 10 distinct fetal movements, kicks, swishes, or rolls.",
        clinicalContextFr = "La norme clinique est de ressentir 10 mouvements en moins de 2 heures lorsque la maman est au repos sur le côté gauche. Une diminution nette nécessite une consultation sans délai.",
        clinicalContextEn = "Clinical recommendation is noting 10 movements within 2 hours while resting on the left side. Any persistent reduction warrants immediate maternity assessment.",
        doctorQuestionFr = "À quel moment de la journée me conseillez-vous d'effectuer ma session de compte des mouvements ?",
        doctorQuestionEn = "Which time of day is optimal for conducting my daily kick counting session?"
    )
)

@Composable
fun PregnancyGlossarySection(
    isFrench: Boolean,
    modifier: Modifier = Modifier
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf(GlossaryCategory.ALL) }
    var expandedItemId by remember { mutableStateOf<String?>(null) }

    val filteredList = remember(searchQuery, selectedCategory, isFrench) {
        GLOSSARY_ITEMS.filter { item ->
            val matchesCategory = (selectedCategory == GlossaryCategory.ALL) || (item.category == selectedCategory)
            val query = searchQuery.trim().lowercase()
            val matchesSearch = query.isEmpty() ||
                    item.acronym.lowercase().contains(query) ||
                    item.fullNameFr.lowercase().contains(query) ||
                    item.fullNameEn.lowercase().contains(query) ||
                    item.definitionFr.lowercase().contains(query) ||
                    item.definitionEn.lowercase().contains(query)
            matchesCategory && matchesSearch
        }
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("pregnancy_glossary_section"),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Hero Header Card
        Surface(
            color = Color(0xFF92400E).copy(alpha = 0.08f),
            shape = RoundedCornerShape(14.dp),
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
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF92400E).copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.MenuBook,
                            contentDescription = null,
                            tint = Color(0xFF92400E),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Column {
                        Text(
                            text = if (isFrench) "Glossaire Médical de Grossesse & PMA" else "Pregnancy & IVF Medical Glossary",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E1905)
                        )
                        Text(
                            text = if (isFrench) "Décryptez les sigles (FET, hCG, DPNI) pour échanger sereinement avec vos médecins"
                            else "Decode medical acronyms (FET, hCG, NIPT) to communicate with doctors with total confidence",
                            fontSize = 11.sp,
                            color = Color(0xFF78350F)
                        )
                    }
                }
            }
        }

        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    text = if (isFrench) "Rechercher un sigle ou terme (ex: FET, hCG, NIPT, APGAR)..."
                    else "Search acronym or term (e.g., FET, hCG, NIPT, APGAR)...",
                    fontSize = 12.sp,
                    color = Color(0xFF78350F).copy(alpha = 0.6f)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color(0xFF92400E),
                    modifier = Modifier.size(20.dp)
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(
                        onClick = { searchQuery = "" },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear search",
                            tint = Color(0xFF92400E),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF92400E),
                unfocusedBorderColor = Color(0xFFBFA278),
                focusedTextColor = Color(0xFF2E1905),
                unfocusedTextColor = Color(0xFF2E1905),
                focusedContainerColor = Color(0xFFFAF4E8),
                unfocusedContainerColor = Color(0xFFFAF4E8)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("glossary_search_bar")
        )

        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            GlossaryCategory.values().forEach { cat ->
                val isSelected = selectedCategory == cat
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = if (isSelected) Color(0xFF92400E) else Color(0xFFFAF4E8),
                    border = BorderStroke(1.dp, if (isSelected) Color(0xFF92400E) else Color(0xFFBFA278)),
                    modifier = Modifier
                        .clickable { selectedCategory = cat }
                        .testTag("glossary_cat_${cat.name.lowercase()}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(text = cat.iconName, fontSize = 11.sp)
                        Text(
                            text = if (isFrench) cat.titleFr else cat.titleEn,
                            fontSize = 11.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color.White else Color(0xFF3B2814)
                        )
                    }
                }
            }
        }

        // Results counter
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = if (isFrench) "${filteredList.size} terme(s) médical(aux) répertorié(s)"
                else "${filteredList.size} medical term(s) cataloged",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF78350F)
            )
            if (filteredList.isNotEmpty()) {
                Text(
                    text = if (isFrench) "Touchez une fiche pour la déplier" else "Tap a card to expand",
                    fontSize = 10.sp,
                    fontStyle = FontStyle.Italic,
                    color = Color(0xFF78350F).copy(alpha = 0.8f)
                )
            }
        }

        // Glossary Cards List
        if (filteredList.isEmpty()) {
            Surface(
                color = Color(0xFFFAF4E8),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color(0xFFBFA278).copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.SearchOff,
                        contentDescription = null,
                        tint = Color(0xFF92400E).copy(alpha = 0.6f),
                        modifier = Modifier.size(36.dp)
                    )
                    Text(
                        text = if (isFrench) "Aucun terme ne correspond à votre recherche" else "No matching medical terms found",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF3B2814)
                    )
                    Text(
                        text = if (isFrench) "Essayez avec un sigle comme 'FET', 'NIPT', 'PIO' ou réinitialisez le filtre."
                        else "Try searching an acronym like 'FET', 'NIPT', 'PIO' or clear the filter.",
                        fontSize = 11.sp,
                        color = Color(0xFF78350F)
                    )
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                filteredList.forEach { item ->
                    val isExpanded = expandedItemId == item.id
                    GlossaryCardItem(
                        item = item,
                        isExpanded = isExpanded,
                        isFrench = isFrench,
                        onToggleExpand = {
                            expandedItemId = if (isExpanded) null else item.id
                        },
                        onCopyQuestion = { question ->
                            clipboardManager.setText(AnnotatedString(question))
                            Toast.makeText(
                                context,
                                if (isFrench) "Question copiée pour votre consultation !" else "Question copied for doctor consultation!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GlossaryCardItem(
    item: GlossaryItem,
    isExpanded: Boolean,
    isFrench: Boolean,
    onToggleExpand: () -> Unit,
    onCopyQuestion: (String) -> Unit
) {
    Surface(
        color = Color(0xFFFAF4E8),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(
            width = if (isExpanded) 1.5.dp else 1.dp,
            color = if (isExpanded) Color(0xFF92400E) else Color(0xFFBFA278)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onToggleExpand() }
            .testTag("glossary_item_${item.id}")
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header Row: Acronym badge, Title & Expand arrow
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Acronym Badge
                Surface(
                    color = Color(0xFF92400E),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = item.acronym,
                        color = Color.White,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                // Full Name & Category
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = if (isFrench) item.fullNameFr else item.fullNameEn,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF2E1905)
                    )
                    Text(
                        text = if (isFrench) item.category.titleFr else item.category.titleEn,
                        fontSize = 10.sp,
                        color = Color(0xFF78350F)
                    )
                }

                // Expand / Collapse Chevron
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    tint = Color(0xFF92400E),
                    modifier = Modifier.size(20.dp)
                )
            }

            // Expanded Content
            AnimatedVisibility(
                visible = isExpanded,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Divider(color = Color(0xFFBFA278).copy(alpha = 0.4f))

                    // Definition
                    Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                        Text(
                            text = if (isFrench) "📖 Définition Médicale" else "📖 Medical Definition",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF92400E)
                        )
                        Text(
                            text = if (isFrench) item.definitionFr else item.definitionEn,
                            fontSize = 12.sp,
                            color = Color(0xFF3B2814),
                            lineHeight = 17.sp
                        )
                    }

                    // Clinical Context / Application for Fatou's Pregnancy
                    Surface(
                        color = Color(0xFF92400E).copy(alpha = 0.06f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFF92400E).copy(alpha = 0.2f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Text(
                                text = if (isFrench) "💡 Repère Clinique & Contexte" else "💡 Clinical Milestone & Context",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF92400E)
                            )
                            Text(
                                text = if (isFrench) item.clinicalContextFr else item.clinicalContextEn,
                                fontSize = 11.sp,
                                color = Color(0xFF2E1905),
                                lineHeight = 16.sp
                            )
                        }
                    }

                    // Suggested Question for Doctor Visit
                    Surface(
                        color = Color(0xFF16A34A).copy(alpha = 0.08f),
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(1.dp, Color(0xFF16A34A).copy(alpha = 0.3f)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ChatBubbleOutline,
                                        contentDescription = null,
                                        tint = Color(0xFF166534),
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Text(
                                        text = if (isFrench) "Question à poser au médecin" else "Question to ask your doctor",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF166534)
                                    )
                                }
                                val questionText = if (isFrench) item.doctorQuestionFr else item.doctorQuestionEn
                                TextButton(
                                    onClick = { onCopyQuestion(questionText) },
                                    contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                                    modifier = Modifier.height(28.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.ContentCopy,
                                        contentDescription = "Copy",
                                        tint = Color(0xFF166534),
                                        modifier = Modifier.size(13.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (isFrench) "Copier" else "Copy",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF166534)
                                    )
                                }
                            }
                            Text(
                                text = if (isFrench) "« ${item.doctorQuestionFr} »" else "“${item.doctorQuestionEn}”",
                                fontSize = 11.sp,
                                fontStyle = FontStyle.Italic,
                                color = Color(0xFF14532D),
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

package com.example.data

data class WeeklyMilestone(
    val week: Int,
    val babySizeDescription: String,
    val commonSymptoms: List<String>,
    val nutritionTip: String,
    val clinicalCheckup: String
)

object MilestoneLocalDataSource {
    private val milestones = mapOf(
        1 to WeeklyMilestone(1, "Graine de pavot (0.1 mm)", listOf("Fatigue légère", "Sensibilité mammaire"), "Acide folique quotidien et hydratation", "Préparation endométriale"),
        2 to WeeklyMilestone(2, "Graine de sésame (0.2 mm)", listOf("Tiraillements utérins", "Ballonnements"), "Aliments riches en antioxydants", "Échographie endomètre"),
        3 to WeeklyMilestone(3, "Graine de chia (0.3 mm)", listOf("Implantation active", "Fatigue passagère"), "Vitamines prénatales", "Veille du transfert J5"),
        4 to WeeklyMilestone(4, "Graine de pavot (1.0 mm)", listOf("Tensions bas-ventre", "Somnolence"), "Protéines de qualité", "Attente premier dosage Bêta-hCG"),
        5 to WeeklyMilestone(5, "Pépin de pomme (2.0 mm)", listOf("Nausées matinales", "Odeurs sensibles"), "Repas légers et fréquents", "Dosages Bêta-hCG sériés"),
        6 to WeeklyMilestone(6, "Pois chiche (4.0 mm)", listOf("Fatigue intense", "Tensions des seins"), "Hydratation renforcée", "Première activité cardiaque"),
        7 to WeeklyMilestone(7, "Myrtille bleue (1.0 cm)", listOf("Envies fréquentes d'uriner", "Nausées"), "Aliments riches en vitamine B6", "Échographie de viabilité"),
        8 to WeeklyMilestone(8, "Framboise (1.6 cm)", listOf("Saute d'humeur", "Tiraillements ligamentaires"), "Fibres et fruits frais", "Formation des cavités cardiaques"),
        9 to WeeklyMilestone(9, "Olive verte (2.3 cm)", listOf("Fatigue persistante", "Changements cutanés"), "Calcium et magnésium", "Fin organogenèse embryonnaire"),
        10 to WeeklyMilestone(10, "Fraise des bois (3.1 cm)", listOf("Atténuation des nausées", "Énergie fluctuante"), "Fer et légumes verts", "Consultation de graduation"),
        11 to WeeklyMilestone(11, "Figue douce (4.1 cm)", listOf("Peau éclatante", "Appétit changeant"), "Hydratation et oméga-3", "Bilan sanguin T1"),
        12 to WeeklyMilestone(12, "Prune juteuse (5.4 cm · 14 g)", listOf("Stabilisation hormonale", "Disparition progressive des nausées"), "Alimentation équilibrée riche en vitamines", "Échographie T1 (Clarté nucale) & DPNI"),
        13 to WeeklyMilestone(13, "Pêche veloutée (7.4 cm)", listOf("Gain d'énergie", "Léger appétit accru"), "Protéines et calcium", "Bilan fin 1er Trimestre"),
        14 to WeeklyMilestone(14, "Citron Meyer (8.7 cm)", listOf("Bien-être retrouvé", "Tension ligamentaire modérée"), "Vitamine D et calcium", "Entrée sereine en T2"),
        15 to WeeklyMilestone(15, "Pomme dorée (10.1 cm)", listOf("Congestion nasale légère", "Cheveux brillants"), "Hydratation et fer", "Consultation 4e mois"),
        16 to WeeklyMilestone(16, "Avocat Hass (11.6 cm)", listOf("Léger essoufflement", "Bons apports sanguins"), "Aliments riches en fer", "Contrôle tensionnel"),
        17 to WeeklyMilestone(17, "Poire conférence (13.0 cm)", listOf("Sensibilité des gencives", "Appétit gourmand"), "Vitamine C et agrumes", "Bilan sérique"),
        18 to WeeklyMilestone(18, "Poivron jaune (14.2 cm)", listOf("Premiers effleurements de mouvements", "Légers maux de dos"), "Posture et étirements doux", "Préparation écho T2"),
        19 to WeeklyMilestone(19, "Mangue mûre (15.3 cm)", listOf("Crampes occasionnelles aux mollets", "Peau sèche"), "Magnésium et eau minérale", "Surveillance croissance"),
        20 to WeeklyMilestone(20, "Petite banane (25.6 cm)", listOf("Mi-grossesse !", "Léger basculement du centre de gravité"), "Équilibre nutritionnel", "Échographie morphologique T2"),
        21 to WeeklyMilestone(21, "Carotte géante (26.7 cm)", listOf("Coups de pied plus nets", "Digestion ralentie"), "Fibres et céréales complètes", "Analyse d'urine mensuelle"),
        22 to WeeklyMilestone(22, "Papaye (27.8 cm)", listOf("Ventre visible et rebondi", "Légers œdèmes chevilles"), "Potassium et tisanes douces", "Morphologie niveau II"),
        23 to WeeklyMilestone(23, "Aubergine ronde (28.9 cm)", listOf("Mouvements réguliers", "Sensations de chaleur"), "Hydratation abondante", "Contrôle du col"),
        24 to WeeklyMilestone(24, "Cantaloup parfumé (30.0 cm)", listOf("Seuil de viabilité atteint", "Fatigue de fin de journée"), "Fer et protéines", "Test HGPO diabète gestationnel"),
        25 to WeeklyMilestone(25, "Chou-fleur (34.6 cm)", listOf("Voix de maman perçue", "Sommeil parfois agité"), "Magnésium et relaxation", "Contrôle anticorps"),
        26 to WeeklyMilestone(26, "Courgette ronde (35.6 cm)", listOf("Yeux s'entrouvrant", "Contractions de Braxton Hicks légères"), "Petits repas fréquents", "Consultation 6e mois"),
        27 to WeeklyMilestone(27, "Chou kale (36.6 cm)", listOf("Fin du 2e trimestre", "Essoufflement modéré"), "Fer et calcium", "Bilan rénal"),
        28 to WeeklyMilestone(28, "Gros ananas (37.6 cm · 1.0 kg)", listOf("Entrée en T3", "Sommeil nécessitant des coussins de soutien"), "Protéines et vitamines", "Injection anti-D si besoin"),
        29 to WeeklyMilestone(29, "Courge butternut (38.6 cm)", listOf("Mouvements vigoureux", "Légers reflux acides"), "Repas fractionnés", "Consultation 7e mois"),
        30 to WeeklyMilestone(30, "Chou blanc (39.9 cm)", listOf("Pic de liquide amniotique", "Fatigue accrue"), "Hydratation et repos", "Planification écho T3"),
        31 to WeeklyMilestone(31, "Noix de coco (41.1 cm)", listOf("Espace utérin restreint", "Sensations de chaleur"), "Petites portions digestes", "Suivi tensionnel"),
        32 to WeeklyMilestone(32, "Courge spaghetti (42.4 cm)", listOf("Position céphalique fréquente", "Essoufflement"), "Fer et calcium", "Échographie T3 finale"),
        33 to WeeklyMilestone(33, "Ananas Victoria (43.7 cm)", listOf("Anticorps transmis", "Pression pelvienne"), "Massages hydratants", "Consultation 8e mois"),
        34 to WeeklyMilestone(34, "Cantaloup mûr (45.0 cm)", listOf("Surfactant mature", "Contractions d'entraînement"), "Relaxation et respiration", "Consultation anesthésique"),
        35 to WeeklyMilestone(35, "Melon miel (46.2 cm)", listOf("Stockage de graisses", "Pression sur la vessie"), "Hydratation contrôlée", "Dépistage Streptocoque B"),
        36 to WeeklyMilestone(36, "Grande papaye (47.4 cm)", listOf("Descente dans le bassin", "Soulagement respiratoire"), "Petits repas légers", "Consultation 9e mois"),
        37 to WeeklyMilestone(37, "Pastèque royale (48.6 cm)", listOf("Terme précoce", "Imminence de la rencontre"), "Énergie et calme", "Monitoring hebdomadaire"),
        38 to WeeklyMilestone(38, "Citrouille ronde (49.8 cm)", listOf("Poigne ferme", "Contractions régulières possibles"), "Aliments énergétiques", "Vérification poche des eaux"),
        39 to WeeklyMilestone(39, "Grande pastèque (50.7 cm)", listOf("Maturité totale", "Imminence du travail"), "Repos et sérénité", "Surveillance du col"),
        40 to WeeklyMilestone(40, "Pastèque sucre (51.5 cm · 3.4 kg)", listOf("TERME ATTEINT (22 Juil 2027)", "Accueil de votre petit prince !"), "Félicitations !", "Jour de l'Accouchement")
    )

    fun getMilestone(week: Int): WeeklyMilestone {
        return milestones[week] ?: milestones[12]!!
    }
}

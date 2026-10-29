package com.example.data

data class PregnancyWeek(
    val week: Int,
    val trimester: Int,
    val title: String,
    val dates: String,
    val fruit: String, // Represents realistic baby/fetus developmental description in the womb
    val metrics: String,
    val heartRate: Int,
    val icon: String,
    val morpho: String,
    val inUtero: String,
    val steps: List<String>,
    val advice: String
)

object PregnancyRepository {
    val weeks = listOf(
        PregnancyWeek(
            week = 1, trimester = 1, title = "Semaine 1", dates = "15 Oct - 21 Oct 2026",
            fruit = "Blastocyste précurseur", metrics = "0.1 mm · 0.01 g", heartRate = 0, icon = "🌱",
            morpho = "Stade cellulaire précurseur. Préparation du lit vasculaire de l'endomètre utérin sous œstrogènes.",
            inUtero = "La muqueuse utérine se gorge de nutriments pour offrir le nid parfait au futur blastocyste.",
            steps = listOf("Date des dernières règles calculée.", "Préparation endométriale active.", "Prise quotidienne d'acide folique."),
            advice = "Accordez-vous des moments de sérénité et privilégiez une hydratation abondante."
        ),
        PregnancyWeek(
            week = 2, trimester = 1, title = "Semaine 2", dates = "22 Oct - 28 Oct 2026",
            fruit = "Nidation endométriale", metrics = "0.2 mm · 0.02 g", heartRate = 0, icon = "🌾",
            morpho = "Optimisation de l'endomètre en aspect trilaminaire supérieur à 8 mm d'épaisseur.",
            inUtero = "L'environnement utérin est parfaitement synchronisé pour la fenêtre d'implantation idéale.",
            steps = listOf("Échographie de contrôle de l'endomètre.", "Vérification épaisseur supérieure à 8 millimètres.", "Introduction des injections de progestérone."),
            advice = "Maintenez vos heures de traitement de façon très ponctuelle pour un synchronisme optimal."
        ),
        PregnancyWeek(
            week = 3, trimester = 1, title = "Semaine 3", dates = "29 Oct - 04 Nov 2026",
            fruit = "Blastocyste J5 expansé", metrics = "0.3 mm · 0.05 g", heartRate = 0, icon = "💧",
            morpho = "Blastocyste expansé au stade d'éclosion pré-transfert au laboratoire d'embryologie.",
            inUtero = "Repos complet de l'utérus avant l'introduction délicate du cathéter de transfert sous échographie.",
            steps = listOf("Veille du transfert embryonnaire.", "Confirmation finale du blastocyste euploïde 46,XY.", "Hydratation optimale."),
            advice = "Dormez d'un sommeil réparateur et conservez une confiance totale dans ce protocole."
        ),
        PregnancyWeek(
            week = 4, trimester = 1, title = "Semaine 4", dates = "05 Nov - 11 Nov 2026",
            fruit = "Embryon en nidation", metrics = "1.0 mm · 0.1 g", heartRate = 90, icon = "✨",
            morpho = "Implantation du blastocyste J5 euploïde. Le trophoblaste s'ancre délicatement dans l'endomètre.",
            inUtero = "L'embryon mâle prend racine. Les premières sécrétions de l'hormone Bêta-hCG débutent.",
            steps = listOf("Transfert blastocyste réussi avec succès.", "Nidation active dans la cavité utérine.", "Attente sereine du premier dosage hormonal."),
            advice = "Marchez calmement au grand air et gardez les pieds et le bas du corps bien au chaud."
        ),
        PregnancyWeek(
            week = 5, trimester = 1, title = "Semaine 5", dates = "12 Nov - 18 Nov 2026",
            fruit = "Sac gestationnel & Vésicule", metrics = "2.0 mm · 0.2 g", heartRate = 110, icon = "🍏",
            morpho = "Différenciation en disque embryonnaire. Le sac gestationnel est nettement visualisé.",
            inUtero = "La vésicule vitelline nourrit l'embryon avant que le placenta ne prenne le relais.",
            steps = listOf("Dosages Bêta-hCG sériés.", "Confirmation du doublement exponentiel des taux.", "Vérification de la progestérone."),
            advice = "Fractionnez vos repas légers pour prévenir l'inconfort gastrique matinal."
        ),
        PregnancyWeek(
            week = 6, trimester = 1, title = "Semaine 6", dates = "19 Nov - 25 Nov 2026",
            fruit = "Fœtus · Premiers battements", metrics = "4.0 mm · 0.4 g", heartRate = 125, icon = "🫘",
            morpho = "Fermeture du tube neural. Les ébauches des yeux et des oreilles apparaissent.",
            inUtero = "Les toutes premières pulsations du petit tube cardiaque embryonnaire s'activent !",
            steps = listOf("Premières pulsations cardiaques enregistrées.", "Poursuite du soutien en progestérone.", "Repos dès les premiers signes de fatigue."),
            advice = "Écoutez votre corps : une courte sieste l'après-midi aide l'organisme à s'adapter."
        ),
        PregnancyWeek(
            week = 7, trimester = 1, title = "Semaine 7", dates = "26 Nov - 02 Déc 2026",
            fruit = "Fœtus · Bourgeons de membres", metrics = "1.0 cm · 0.5 g", heartRate = 138, icon = "🫐",
            morpho = "Bourgeons des bras et jambes visibles. L'embryon double de taille en sept jours.",
            inUtero = "Le petit cœur bat à 138 battements par minute, perceptible en échographie Doppler.",
            steps = listOf("Échographie de viabilité formelle.", "Visualisation du sac vitellin et de l'embryon.", "Rythme régulier confirmé."),
            advice = "Portez des vêtements amples et évitez toute compression au niveau abdominal."
        ),
        PregnancyWeek(
            week = 8, trimester = 1, title = "Semaine 8", dates = "03 Déc - 09 Déc 2026",
            fruit = "Fœtus · Doigts & paupières", metrics = "1.6 cm · 1.0 g", heartRate = 152, icon = "🍓",
            morpho = "Les doigts et orteils se modèlent. Les paupières recouvrent délicatement les yeux.",
            inUtero = "L'embryon réalise de minuscules mouvements spontanés dans son cocon amniotique.",
            steps = listOf("Formation des quatre cavités cardiaques.", "Consultation d'évolution obstétrique.", "Bonne hydratation continue."),
            advice = "Les infusions de gingembre frais apaisent naturellement les nausées passagères."
        ),
        PregnancyWeek(
            week = 9, trimester = 1, title = "Semaine 9", dates = "10 Déc - 16 Déc 2026",
            fruit = "Fœtus · Organogenèse active", metrics = "2.3 cm · 2.0 g", heartRate = 165, icon = "🫒",
            morpho = "Passage du statut d'embryon à celui de fœtus. Les articulations des coudes et genoux plient.",
            inUtero = "Les organes génitaux internes de votre petit garçon continuent leur différenciation.",
            steps = listOf("Fin de l'organogenèse embryonnaire principale.", "Planification du sevrage progressif des hormones.", "Examen de tension artérielle."),
            advice = "Respirez lentement par le ventre lors des moments de tension émotionnelle."
        ),
        PregnancyWeek(
            week = 10, trimester = 1, title = "Semaine 10", dates = "17 Déc - 23 Déc 2026",
            fruit = "Fœtus · Calcification osseuse", metrics = "3.1 cm · 4.0 g", heartRate = 162, icon = "🍓",
            morpho = "Les os commencent à se calcifier. La silhouette fœtale s'allonge harmonieusement.",
            inUtero = "Le placenta prend entièrement la relève hormonale et nutritionnelle.",
            steps = listOf("Consultation de graduation et relais médical.", "Transmission du dossier complet à l'obstétricien.", "Arrêt guidé des injections."),
            advice = "Bravo pour cette étape de graduation franchie avec plein succès !"
        ),
        PregnancyWeek(
            week = 11, trimester = 1, title = "Semaine 11", dates = "24 Déc - 30 Déc 2026",
            fruit = "Fœtus · Profil facial complet", metrics = "4.1 cm · 7.0 g", heartRate = 160, icon = "🪴",
            morpho = "Le profil facial s'affine nettement, les ongles commencent à se former aux extrémités.",
            inUtero = "Le fœtus bouge activement dans le liquide amniotique, s'étirant et se tournant.",
            steps = listOf("Préparation du bilan du premier trimestre.", "Évaluation de la clarté nucale à venir.", "Hydratation renforcée."),
            advice = "Partagez vos émotions et parlez à votre bébé, il commence à percevoir les sons."
        ),
        PregnancyWeek(
            week = 12, trimester = 1, title = "Semaine 12", dates = "31 Déc - 06 Jan 2027",
            fruit = "Fœtus · Échographie T1 & DPNI", metrics = "5.4 cm · 14 g", heartRate = 158, icon = "🫐",
            morpho = "Clarté nucale et os propre du nez visualisés. Organes pleinement en place.",
            inUtero = "Le fœtus avale du liquide amniotique et commence à faire ses premiers mouvements respiratoires.",
            steps = listOf("Échographie T1 de dépistage officiel (RMA of New York).", "Test DPNI sanguin de trisomie.", "Validation du premier trimestre."),
            advice = "Savourez la beauté de cette échographie du premier trimestre avec votre conjoint."
        ),
        PregnancyWeek(
            week = 13, trimester = 1, title = "Semaine 13", dates = "07 Jan - 13 Jan 2027",
            fruit = "Fœtus · Fin du 1er trimestre", metrics = "7.4 cm · 23 g", heartRate = 155, icon = "🍑",
            morpho = "Empreintes digitales uniques en formation. Risque de fausse couche considérablement réduit.",
            inUtero = "Le placenta assure une nutrition optimale. Le confort maternel s'améliore nettement.",
            steps = listOf("Clôture du 1er trimestre réussie.", "Annonce sécurisée aux proches.", "Suivi obstétrical du 2ème trimestre planifié."),
            advice = "Reprenez une activité physique douce comme le yoga prénatal ou la marche."
        ),
        PregnancyWeek(
            week = 14, trimester = 2, title = "Semaine 14", dates = "14 Jan - 20 Jan 2027",
            fruit = "Fœtus · Mouvements réflexes", metrics = "8.7 cm · 43 g", heartRate = 152, icon = "🍋",
            morpho = "Le cou s'allonge et le menton ne repose plus sur la poitrine. Premiers sourcils.",
            inUtero = "Le fœtus fait des grimaces et réagit aux pressions exercées sur l'abdomen.",
            steps = listOf("Entrée dans le deuxième trimestre.", "Surveillance du poids et de la tension.", "Vitamines prénatales adaptées."),
            advice = "Portez des chaussures confortables et surélevez vos jambes le soir."
        ),
        PregnancyWeek(
            week = 15, trimester = 2, title = "Semaine 15", dates = "21 Jan - 27 Jan 2027",
            fruit = "Fœtus · Squelette en ossification", metrics = "10.1 cm · 70 g", heartRate = 150, icon = "🍎",
            morpho = "Les os se densifient et deviennent visibles à l'échographie. Peau fine translucide.",
            inUtero = "Il perçoit les bruits sourds de votre voix et les battements de votre cœur.",
            steps = listOf("Consultation de suivi prénatal standard.", "Écoute des battements cardiaques fœtaux.", "Exercices de respiration."),
            advice = "Chantez des comptines douces, votre bébé y est sensible."
        ),
        PregnancyWeek(
            week = 16, trimester = 2, title = "Semaine 16", dates = "28 Jan - 03 Fév 2027",
            fruit = "Fœtus · Audition active", metrics = "11.6 cm · 100 g", heartRate = 148, icon = "🥑",
            morpho = "Les muscles du dos se renforcent, la tête se redresse bien droite.",
            inUtero = "Les premiers frémissements de mouvements fœtaux peuvent commencer à se faire ressentir.",
            steps = listOf("Planification de la grande échographie morphologique T2.", "Bilan sanguin de contrôle.", "Hydratation optimale."),
            advice = "Restez à l'écoute de votre corps pour percevoir les tout premiers papillons."
        ),
        PregnancyWeek(
            week = 17, trimester = 2, title = "Semaine 17", dates = "04 Fév - 10 Fév 2027",
            fruit = "Fœtus · Tissu adipeux brun", metrics = "13.0 cm · 140 g", heartRate = 147, icon = "🍐",
            morpho = "Formation de la graisse brune essentielle pour la régulation thermique future.",
            inUtero = "Le squelette cartilagineux se durcit en os solides.",
            steps = listOf("Contrôle de la glycémie et tension.", "Vérification de l'apport en calcium et fer.", "Marche quotidienne."),
            advice = "Consommez des aliments riches en fer et vitamine C pour favoriser l'absorption."
        ),
        PregnancyWeek(
            week = 18, trimester = 2, title = "Semaine 18", dates = "11 Fév - 17 Fév 2027",
            fruit = "Fœtus · Cycles veille / sommeil", metrics = "14.2 cm · 190 g", heartRate = 146, icon = "🫑",
            morpho = "La myéline entoure les nerfs. Les oreilles atteignent leur position définitive.",
            inUtero = "Le bébé bâille, avale et alterne des phases de sommeil profond et d'éveil actif.",
            steps = listOf("Préparation de la chambre et du cocon.", "Suivi de la courbe de poids maternel.", "Repos adapté."),
            advice = "Dormez de préférence sur le côté gauche pour optimiser le flux sanguin utérin."
        ),
        PregnancyWeek(
            week = 19, trimester = 2, title = "Semaine 19", dates = "18 Fév - 24 Fév 2027",
            fruit = "Fœtus · Vernix caseosa protectrice", metrics = "15.3 cm · 240 g", heartRate = 145, icon = "🥭",
            morpho = "Sécrétion du vernix caseosa, une substance cireuse protégeant la peau fœtale.",
            inUtero = "Les sens s'affinent : goût, ouïe, toucher et odorat se développent en réseau.",
            steps = listOf("Sélection des cours de préparation à l'accouchement.", "Exercices doux de bassin.", "Écoute fœtale."),
            advice = "Appliquez une crème hydratante ou une huile naturelle sur le ventre pour prévenir les vergetures."
        ),
        PregnancyWeek(
            week = 20, trimester = 2, title = "Semaine 20", dates = "25 Fév - 03 Mar 2027",
            fruit = "Fœtus · Mi-parcours morphologique", metrics = "25.6 cm · 300 g", heartRate = 144, icon = "🍌",
            morpho = "Cap des 20 semaines atteint ! Morphologie complète et détaillée.",
            inUtero = "Les mouvements de votre petit garçon sont désormais bien perceptibles et réguliers.",
            steps = listOf("Grande Échographie Morphologique T2 officielle.", "Vérification cardiaque et placentaire approfondie.", "Célébration de la mi-parcours."),
            advice = "Un moment inoubliable : profitez pleinement de cette échographie détaillée du 2ème trimestre !"
        ),
        PregnancyWeek(
            week = 21, trimester = 2, title = "Semaine 21", dates = "04 Mar - 10 Mar 2027",
            fruit = "Fœtus · Moelle osseuse productrice", metrics = "26.7 cm · 360 g", heartRate = 143, icon = "🥕",
            morpho = "La moelle osseuse commence à produire activement les globules sanguins.",
            inUtero = "Le système digestif s'exerce en avalant régulièrement du liquide amniotique.",
            steps = listOf("Analyse des résultats de l'échographie T2 avec l'obstétricien.", "Bilan dentaire de grossesse.", "Hydratation."),
            advice = "Maintenez une bonne hygiène bucco-dentaire et consultez votre dentiste si nécessaire."
        ),
        PregnancyWeek(
            week = 22, trimester = 2, title = "Semaine 22", dates = "11 Mar - 17 Mar 2027",
            fruit = "Fœtus · Cils & sourcils formés", metrics = "27.8 cm · 430 g", heartRate = 142, icon = "🫐",
            morpho = "Les sourcils et les cils sont parfaitement dessinés. Les ongles recouvrent les doigts.",
            inUtero = "Le bébé réagit vivement aux bruits extérieurs brusques par des sursauts.",
            steps = listOf("Surveillance de la tension artérielle.", "Vérification du col utérin.", "Marche et étirements doux."),
            advice = "Évitez les environnements trop bruyants et privilégiez une atmosphère musicale apaisante."
        ),
        PregnancyWeek(
            week = 23, trimester = 2, title = "Semaine 23", dates = "18 Mar - 24 Mar 2027",
            fruit = "Fœtus · Vaisseaux & capillaires actifs", metrics = "28.9 cm · 500 g", heartRate = 142, icon = "🍆",
            morpho = "Les poumons fabriquent le surfactant, substance vitale pour la respiration future.",
            inUtero = "Les petits vaisseaux sanguins sous la peau donnent une teinte rosée au fœtus.",
            steps = listOf("Suivi mensuel obstétrical.", "Contrôle de l'hémoglobine et de la ferritinémie.", "Repos régulier."),
            advice = "Surélevez vos jambes et pratiquez le drainage lymphatique léger en cas de jambes lourdes."
        ),
        PregnancyWeek(
            week = 24, trimester = 2, title = "Semaine 24", dates = "25 Mar - 31 Mar 2027",
            fruit = "Fœtus · Seuil de viabilité fœtale", metrics = "30.0 cm · 600 g", heartRate = 140, icon = "🍈",
            morpho = "Cap majeur de viabilité extra-utérine. Les alvéoles pulmonaires se multiplient.",
            inUtero = "Le fœtus réagit aux caresses sur le ventre en poussant délicatement en retour.",
            steps = listOf("Consultation du 6ème mois.", "Dépistage du diabète gestationnel (HGPO si prescrit).", "Enregistrement des coups."),
            advice = "Commencez à suivre régulièrement les mouvements quotidiens de votre bébé."
        ),
        PregnancyWeek(
            week = 25, trimester = 2, title = "Semaine 25", dates = "01 Avr - 07 Avr 2027",
            fruit = "Fœtus · Cheveux & réactivité sonore", metrics = "34.6 cm · 660 g", heartRate = 140, icon = "🥦",
            morpho = "Les cheveux poussent avec leur pigmentation. La colonne vertébrale se consolide.",
            inUtero = "Il distingue la voix de sa maman de celle de son papa et y répond.",
            steps = listOf("Discussion autour du projet de naissance.", "Choix de la maternité.", "Exercices de respiration."),
            advice = "Parlez-lui souvent : le lien affectif prénatal se fortifie de jour en jour."
        ),
        PregnancyWeek(
            week = 26, trimester = 2, title = "Semaine 26", dates = "08 Avr - 14 Avr 2027",
            fruit = "Fœtus · Yeux s'ouvrent au clignement", metrics = "35.6 cm · 760 g", heartRate = 139, icon = "🥒",
            morpho = "Les yeux s'entrouvrent et perçoivent les variations de lumière à travers la paroi.",
            inUtero = "Le cerveau présente des ondes cérébrales similaires à celles d'un nouveau-né endormi.",
            steps = listOf("Contrôle glycémique et tension.", "Vérification du carnet de suivi.", "Marche douce."),
            advice = "Exposez votre ventre à une lumière douce et observez les réactions de votre bébé."
        ),
        PregnancyWeek(
            week = 27, trimester = 2, title = "Semaine 27", dates = "15 Avr - 21 Avr 2027",
            fruit = "Fœtus · Fin du 2ème trimestre", metrics = "36.6 cm · 875 g", heartRate = 138, icon = "🥬",
            morpho = "Clôture du deuxième trimestre ! Le fœtus respire du liquide amniotique régulièrement.",
            inUtero = "Il rêve activement pendant ses phases de sommeil paradoxal.",
            steps = listOf("Bilan de fin de 2ème trimestre.", "Préparation administrative de la maternité.", "Dernières injections ou compléments de fin de protocole."),
            advice = "Félicitations pour avoir franchi le deuxième trimestre avec brio !"
        ),
        PregnancyWeek(
            week = 28, trimester = 3, title = "Semaine 28", dates = "22 Avr - 28 Avr 2027",
            fruit = "Fœtus · Entrée 3ème trimestre", metrics = "37.6 cm · 1.0 kg", heartRate = 138, icon = "🍍",
            morpho = "Ouverture officielle du troisième trimestre. Le système immunitaire élabore ses défenses.",
            inUtero = "Le fœtus occupe une grande partie de l'espace utérin et se positionne progressivement.",
            steps = listOf("Consultation du 7ème mois.", "Injection d'immunoglobulines anti-D si maman de rhesus négatif.", "Vaccination coqueluche."),
            advice = "Prévoyez vos rendez-vous de préparation à l'accouchement et l'anesthésiste."
        ),
        PregnancyWeek(
            week = 29, trimester = 3, title = "Semaine 29", dates = "29 Avr - 05 Mai 2027",
            fruit = "Fœtus · Régulation thermique active", metrics = "38.6 cm · 1.15 kg", heartRate = 137, icon = "🎃",
            morpho = "Le cerveau contrôle désormais la respiration rythmique et la température corporelle.",
            inUtero = "Les os continuent d'emmagasiner le calcium maternel en grande quantité.",
            steps = listOf("Surveillance accrue de la tension artérielle.", "Vérification des apports en calcium et magnésium.", "Repos fractionné."),
            advice = "Consommez des produits laitiers ou des sources végétales riches en calcium."
        ),
        PregnancyWeek(
            week = 30, trimester = 3, title = "Semaine 30", dates = "06 Mai - 12 Mai 2027",
            fruit = "Fœtus · Sommeil paradoxal & rêves", metrics = "39.9 cm · 1.3 kg", heartRate = 136, icon = "🥬",
            morpho = "La peau devient plus lisse grâce à l'accumulation du tissu adipeux sous-cutané.",
            inUtero = "Le fœtus cligne des yeux et réagit aux flashs lumineux extérieurs sur le ventre.",
            steps = listOf("Contrôle échographique de croissance du 3ème trimestre.", "Vérification du volume amniotique.", "Position fœtale."),
            advice = "Pratiquez des exercices de respiration profonde pour soulager le diaphragme."
        ),
        PregnancyWeek(
            week = 31, trimester = 3, title = "Semaine 31", dates = "13 Mai - 19 Mai 2027",
            fruit = "Fœtus · Poussée de croissance rapide", metrics = "41.1 cm · 1.5 kg", heartRate = 135, icon = "🥥",
            morpho = "Les cinq sens fonctionnent à plein régime. Le cerveau développe des milliards de connexions.",
            inUtero = "Les mouvements vigoureux se font plus amples mais l'espace devient plus exigu.",
            steps = listOf("Consultation obstétricale de routine.", "Comptage quotidien des mouvements fœtaux.", "Hydratation."),
            advice = "Notez chaque jour les périodes d'activité de votre bébé pour suivre son bien-être."
        ),
        PregnancyWeek(
            week = 32, trimester = 3, title = "Semaine 32", dates = "20 Mai - 26 Mai 2027",
            fruit = "Fœtus · Ongles & proportions finales", metrics = "42.4 cm · 1.7 kg", heartRate = 135, icon = "🎃",
            morpho = "Les ongles des mains poussent jusqu'au bout des doigts. Cheveux bien fournis.",
            inUtero = "Le bébé prend sa position de naissance définitive (généralement tête en bas / céphalique).",
            steps = listOf("Rendez-vous avec l'anesthésiste de la maternité.", "Validation du dossier obstétrical.", "Valise de maternité préparée."),
            advice = "Finalisez la valise de maternité pour vivre sereinement les dernières semaines."
        ),
        PregnancyWeek(
            week = 33, trimester = 3, title = "Semaine 33", dates = "27 Mai - 02 Jun 2027",
            fruit = "Fœtus · Immunité maternelle acquise", metrics = "43.7 cm · 1.9 kg", heartRate = 134, icon = "🍍",
            morpho = "Les anticorps maternels traversent le placenta pour protéger le bébé après la naissance.",
            inUtero = "Le crâne reste souple (non soudé) pour faciliter le passage lors de l'accouchement.",
            steps = listOf("Suivi prénatal bimensuel.", "Surveillance de l'albuminurie et de la tension.", "Repos des jambes."),
            advice = "Évitez les stations debout prolongées et profitez de moments de repos."
        ),
        PregnancyWeek(
            week = 34, trimester = 3, title = "Semaine 34", dates = "03 Jun - 09 Jun 2027",
            fruit = "Fœtus · Position céphalique idéale", metrics = "45.0 cm · 2.15 kg", heartRate = 133, icon = "🍈",
            morpho = "Le système central nerveux et les poumons atteignent une maturité presque complète.",
            inUtero = "Le fœtus est positionné tête en bas, prêt pour le grand voyage.",
            steps = listOf("Consultation du 8ème mois.", "Vérification de la présentation fœtale (céphalique).", "Plan d'accouchement finalisé."),
            advice = "Préparez doucement votre esprit à l'accueil de votre petit garçon."
        ),
        PregnancyWeek(
            week = 35, trimester = 3, title = "Semaine 35", dates = "10 Jun - 16 Jun 2027",
            fruit = "Fœtus · Organes pleinement matures", metrics = "46.2 cm · 2.4 kg", heartRate = 132, icon = "🍈",
            morpho = "Les reins sont pleinement fonctionnels et le foie élimine les déchets fœtaux.",
            inUtero = "Le bébé arrondit ses joues et sa peau devient rose et rebondie.",
            steps = listOf("Derniers prélèvements vaginaux de dépistage (streptocoque B).", "Consultation anesthésie validée.", "Surveillance des contractions."),
            advice = "Restez sereine, entourez-vous d'ondes positives et de bienveillance."
        ),
        PregnancyWeek(
            week = 36, trimester = 3, title = "Semaine 36", dates = "17 Jun - 23 Jun 2027",
            fruit = "Fœtus · Prêt pour la naissance", metrics = "47.4 cm · 2.6 kg", heartRate = 132, icon = "🫐",
            morpho = "Le vernix caseosa s'épaissit. Le bébé prend une position de descente dans le bassin.",
            inUtero = "Le cœur bat vigoureusement et le bébé s'entraîne à respirer et à téter.",
            steps = listOf("Visite médicale hebdomadaire de fin de grossesse.", "Contrôle du rythme cardiaque fœtal (monitoring).", "Valise prête à l'entrée."),
            advice = "Reposez-vous au maximum et conservez vos documents médicaux à portée de main."
        ),
        PregnancyWeek(
            week = 37, trimester = 3, title = "Semaine 37", dates = "24 Jun - 30 Jun 2027",
            fruit = "Fœtus · Terme précoce (S37)", metrics = "48.6 cm · 2.85 kg", heartRate = 130, icon = "🍉",
            morpho = "Le bébé est considéré à terme précoce. Tous ses systèmes vitaux sont opérationnels.",
            inUtero = "Moins de place pour bouger, le bébé se prépare activement à la naissance.",
            steps = listOf("Monitoring de contrôle de fin de grossesse.", "Vérification des signes de travail.", "Coordonnées de la maternité prêtes."),
            advice = "Soyez attentive aux moindres signes (contractions régulières, perte des eaux)."
        ),
        PregnancyWeek(
            week = 38, trimester = 3, title = "Semaine 38", dates = "01 Juil - 07 Juil 2027",
            fruit = "Fœtus · Descente utérine active", metrics = "49.8 cm · 3.0 kg", heartRate = 130, icon = "🎃",
            morpho = "Le col commence à s'assouplir et à se modifier sous l'action des hormones.",
            inUtero = "Descente de la présentation fœtale dans le bassin maternel.",
            steps = listOf("Consultation obstétricale hebdomadaire.", "Examen du col utérin.", "Vérification du bien-être fœtal."),
            advice = "Marchez un peu chaque jour si vous vous en sentez capable pour faciliter l'engagement."
        ),
        PregnancyWeek(
            week = 39, trimester = 3, title = "Semaine 39", dates = "08 Juil - 14 Juil 2027",
            fruit = "Fœtus · Terme accompli", metrics = "50.7 cm · 3.2 kg", heartRate = 128, icon = "🍉",
            morpho = "Terme accompli. Le duvet protecteur (lanugo) a presque entièrement disparu.",
            inUtero = "Le bébé est prêt à faire sa grande entrée dans le monde extérieur.",
            steps = listOf("Dernières vérifications obstétricales.", "Disponibilité de l'équipe médicale de RMA of New York.", "Sérénité absolue."),
            advice = "Respirez profondément, visualisez la rencontre magique avec votre petit garçon."
        ),
        PregnancyWeek(
            week = 40, trimester = 3, title = "Semaine 40", dates = "15 Juil - 22 Juil 2027",
            fruit = "Bébé à terme · Naissance", metrics = "51.5 cm · 3.4 kg", heartRate = 128, icon = "🍉",
            morpho = "Terme officiel de la DPA (22/07/2027). Bébé est pleinement développé et magnifique.",
            inUtero = "Le grand moment est arrivé pour serrer votre petit garçon dans vos bras.",
            steps = listOf("Arrivée à la maternité pour le grand jour !", "Accueil du nouveau-né.", "Bonheur éternel de la famille."),
            advice = "Bienvenue dans cette magnifique aventure de parentalité ! Tout notre amour vous accompagne."
        )
    )
}

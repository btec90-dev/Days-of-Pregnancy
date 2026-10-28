package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.MilestoneLocalDataSource
import com.example.data.PregnancyRepository
import com.example.data.PregnancyWeek
import com.example.ui.components.AudioHelper
import com.example.ui.components.FatouLogo
import com.example.ui.components.PregnancyCountdownCard
import com.example.ui.components.UltrasoundView
import com.example.viewmodel.PregnancyViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun FatouCalendarScreen(viewModel: PregnancyViewModel = viewModel()) {
    val context = LocalContext.current
    val audioHelper = remember { AudioHelper(context) }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    var selectedWeekNumber by remember { mutableStateOf(12) }

    // Sync selectedWeekNumber with Room calculated week on first load or update
    LaunchedEffect(uiState.currentWeek) {
        if (selectedWeekNumber == 12 && uiState.currentWeek != 12) {
            selectedWeekNumber = uiState.currentWeek
        }
    }

    val currentWeek = remember(selectedWeekNumber) {
        PregnancyRepository.weeks.firstOrNull { it.week == selectedWeekNumber } ?: PregnancyRepository.weeks[11]
    }

    var is3dMode by remember { mutableStateOf(true) }
    var isDopplerPlaying by remember { mutableStateOf(false) }
    var isSpeaking by remember { mutableStateOf(false) }
    var activeTab by remember { mutableStateOf(0) } // 0: FET, 1: Chronologie, 2: Check-lists, 3: Journal
    var journalText by remember { mutableStateOf("") }
    var autoPlayAudio by remember { mutableStateOf(false) }

    val dateFormatter = remember { SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH) }
    val fetDateFormatted = dateFormatter.format(Date(uiState.fetDateMillis))
    val lmpDateFormatted = dateFormatter.format(Date(uiState.lmpDateMillis))

    DisposableEffect(Unit) {
        onDispose {
            audioHelper.shutdown()
        }
    }

    fun playWeekNarration(week: PregnancyWeek) {
        isSpeaking = true
        val fullText = "Semaine ${week.week} de votre grossesse. Votre petit garçon mesure environ ${week.metrics}, ce qui correspond à la taille d'une ${week.fruit}. Développement in utero : ${week.inUtero} Sur le plan morphologique : ${week.morpho} Conseil de bien-être : ${week.advice}"
        audioHelper.speak(
            text = fullText,
            onStart = { isSpeaking = true },
            onDone = { isSpeaking = false }
        )
    }

    fun speakSection(sectionTitle: String, content: String) {
        isSpeaking = true
        audioHelper.speak(
            text = "$sectionTitle. $content",
            onStart = { isSpeaking = true },
            onDone = { isSpeaking = false }
        )
    }

    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    colors = listOf(Color(0xFF382312), Color(0xFF150B04)),
                    radius = 1200f
                )
            )
            .padding(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .shadow(24.dp, RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFFE8D4B4), Color(0xFFC7A170))
                    )
                )
                .border(8.dp, Color(0xFF926335), RoundedCornerShape(24.dp))
                .verticalScroll(scrollState)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    repeat(2) {
                        Box(
                            modifier = Modifier
                                .size(14.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.radialGradient(
                                        listOf(Color(0xFFFFEF99), Color(0xFF9A7B2C))
                                    )
                                )
                                .border(1.dp, Color(0xFF422C0A), CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                FatouLogo()
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "PROTOCOLE FET · SUIVI OBSTÉTRICAL LOCAL (ROOM)",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF593210).copy(alpha = 0.8f),
                    letterSpacing = 1.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Fatou Pregnancy Calendar",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Black,
                    color = Color(0xFF3A200C)
                )
                Text(
                    text = "Base Room • ${uiState.babyName} • RMA of New York",
                    fontSize = 12.sp,
                    fontStyle = FontStyle.Italic,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFF4A2C10),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Telemetry Status Badges with Room Calculated Week
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        color = Color(0xFF3B2814).copy(alpha = 0.1f),
                        shape = RoundedCornerShape(16.dp),
                        border = BorderStroke(1.dp, Color(0xFF593210).copy(alpha = 0.3f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(8.dp)
                                    .background(Color(0xFF16A34A), CircleShape)
                            )
                            Text("Semaine Room : S${uiState.currentWeek} (${uiState.currentDaysInWeek} jours)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E1905))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                PregnancyCountdownCard(viewModel = viewModel)
            }

            Divider(color = Color(0xFF78350F).copy(alpha = 0.3f), thickness = 1.dp)

            // Two Column Layout
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // LEFT / TOP: 40 Doors Grid
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFB88E5D).copy(alpha = 0.6f))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Volets des 40 Semaines",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E1905)
                        )
                        Text(
                            text = "✨ Actuelle : S${uiState.currentWeek}",
                            fontSize = 11.sp,
                            color = Color(0xFF92400E),
                            fontWeight = FontWeight.Bold
                        )
                    }

                    LazyVerticalGrid(
                        columns = GridCells.Fixed(5),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(260.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        items(PregnancyRepository.weeks) { week ->
                            val isActive = week.week == selectedWeekNumber
                            val isCurrentReal = week.week == uiState.currentWeek
                            val cardBg = when {
                                week.trimester == 1 -> Color(0xFFC9DBC9)
                                week.trimester == 2 -> Color(0xFFF6D2c1)
                                else -> Color(0xFFF7E0B2)
                            }
                            val borderColor = if (isActive) Color(0xFFB45309) else Color(0xFF78350F).copy(alpha = 0.3f)

                            Card(
                                onClick = {
                                    selectedWeekNumber = week.week
                                    if (autoPlayAudio) {
                                        playWeekNarration(week)
                                    }
                                },
                                colors = CardDefaults.cardColors(containerColor = cardBg),
                                border = BorderStroke(if (isActive) 2.5.dp else 1.dp, borderColor),
                                modifier = Modifier
                                    .height(72.dp)
                                    .shadow(if (isActive) 6.dp else 2.dp, RoundedCornerShape(10.dp))
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text("S${week.week}", fontSize = 10.sp, fontWeight = FontWeight.Black, color = Color(0xFF2E1905))
                                        if (isCurrentReal) {
                                            Box(
                                                modifier = Modifier
                                                    .size(6.dp)
                                                    .background(Color(0xFFDC2626), CircleShape)
                                            )
                                        }
                                    }
                                    Text(week.icon, fontSize = 20.sp)
                                    Text(
                                        text = week.fruit,
                                        fontSize = 8.sp,
                                        fontWeight = FontWeight.Bold,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        color = Color(0xFF3B2814)
                                    )
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        LegendItem(Color(0xFFC9DBC9), "T1 (S1-13)")
                        LegendItem(Color(0xFFF6D2C1), "T2 (S14-27)")
                        LegendItem(Color(0xFFF7E0B2), "T3 (S28-40)")
                    }
                }

                // RIGHT / BOTTOM: Central Niche
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(12.dp, RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFAF4E8))
                        .border(1.5.dp, Color(0xFFBFA278), RoundedCornerShape(16.dp))
                        .padding(14.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(
                                    text = if (currentWeek.trimester == 1) "PREMIER TRIMESTRE" else if (currentWeek.trimester == 2) "DEUXIÈME TRIMESTRE" else "TROISIÈME TRIMESTRE",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF92400E)
                                )
                                Surface(
                                    color = Color(0xFFB45309).copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = uiState.babyName,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF78350F),
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                    )
                                }
                            }
                            Text(
                                text = "SEMAINE ${currentWeek.week}",
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Black,
                                color = Color(0xFF2E1905)
                            )
                            Text(
                                text = "${currentWeek.dates} • Room Tracking",
                                fontSize = 11.sp,
                                color = Color(0xFF78350F)
                            )
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text("Biométrie & Échelle", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF78350F))
                            Text(currentWeek.metrics, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E1905))
                            Text("Taille : ${currentWeek.fruit}", fontSize = 11.sp, fontStyle = FontStyle.Italic, color = Color(0xFF854D0E))
                        }
                    }

                    // Audio Narration Card
                    Surface(
                        color = Color(0xFFFAF5ED),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.3f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = { playWeekNarration(currentWeek) },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF92400E)),
                                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                                ) {
                                    Icon(
                                        imageVector = if (isSpeaking) Icons.Default.Pause else Icons.Default.PlayArrow,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(if (isSpeaking) "Lecture en cours..." else "Écouter la narration posée", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                                }

                                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Text("Auto-lecture", fontSize = 10.sp, color = Color(0xFF78350F))
                                    Switch(
                                        checked = autoPlayAudio,
                                        onCheckedChange = { autoPlayAudio = it },
                                        modifier = Modifier.scale(0.7f)
                                    )
                                }
                            }

                            Text(
                                text = "Transcription : Semaine ${currentWeek.week}. Votre petit garçon mesure ${currentWeek.metrics}, comparable à une ${currentWeek.fruit}. Développement : ${currentWeek.inUtero}",
                                fontSize = 11.sp,
                                color = Color(0xFF3B2814),
                                fontStyle = FontStyle.Italic,
                                maxLines = 3,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    // Ultrasound / 3D Viewer Component
                    UltrasoundView(
                        weekData = currentWeek,
                        is3dMode = is3dMode,
                        onToggleMode = { is3dMode = it },
                        isDopplerPlaying = isDopplerPlaying,
                        onToggleDoppler = {
                            isDopplerPlaying = !isDopplerPlaying
                            audioHelper.toggleDoppler(currentWeek.heartRate) { isDopplerPlaying = it }
                        }
                    )

                    // Biometrics Cards
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        BiometricCard("Longueur", currentWeek.metrics.split("·").firstOrNull() ?: currentWeek.metrics, Modifier.weight(1f))
                        BiometricCard("Poids Estimé", currentWeek.metrics.split("·").lastOrNull() ?: "14 g", Modifier.weight(1f))
                        BiometricCard("Fruit", currentWeek.fruit, Modifier.weight(1f))
                    }

                    val milestone = remember(currentWeek.week) {
                        MilestoneLocalDataSource.getMilestone(currentWeek.week)
                    }
                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF5ED)),
                        border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.3f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text("🌱 Jalons, Symptômes & Nutrition (Semaine ${currentWeek.week})", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E))
                            Text("• Symptômes fréquents : ${milestone.commonSymptoms.joinToString(", ")}", fontSize = 11.sp, color = Color(0xFF3B2814))
                            Text("• Conseil nutritionnel : ${milestone.nutritionTip}", fontSize = 11.sp, color = Color(0xFF3B2814))
                            Text("• Suivi clinique : ${milestone.clinicalCheckup}", fontSize = 11.sp, fontWeight = FontWeight.Medium, color = Color(0xFF16A34A))
                        }
                    }

                    ClinicalSectionCard("👶 Développement in utero", currentWeek.inUtero) {
                        speakSection("Développement in utero", currentWeek.inUtero)
                    }
                    ClinicalSectionCard("🔬 Morphologie fœtale & Croissance", currentWeek.morpho) {
                        speakSection("Morphologie fœtale", currentWeek.morpho)
                    }

                    Card(
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
                        border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.2f))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text("📋 Démarches Cliniques & Obstétrique", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E))
                                IconButton(
                                    onClick = { speakSection("Démarches cliniques", currentWeek.steps.joinToString(". ")) },
                                    modifier = Modifier.size(24.dp)
                                ) {
                                    Icon(Icons.Default.VolumeUp, contentDescription = null, tint = Color(0xFF92400E), modifier = Modifier.size(16.dp))
                                }
                            }
                            currentWeek.steps.forEach { step ->
                                Text("• $step", fontSize = 11.sp, color = Color(0xFF3B2814))
                            }
                        }
                    }

                    ClinicalSectionCard("💖 Conseils de bien-être", currentWeek.advice) {
                        speakSection("Conseils de bien-être", currentWeek.advice)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            onClick = { if (selectedWeekNumber > 1) selectedWeekNumber-- },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706).copy(alpha = 0.2f)),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text("← S-1", color = Color(0xFF4A2C10), fontWeight = FontWeight.Bold)
                        }

                        Text("S$selectedWeekNumber / 40", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E1905))

                        Button(
                            onClick = { if (selectedWeekNumber < 40) selectedWeekNumber++ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD97706).copy(alpha = 0.2f)),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp)
                        ) {
                            Text("S+1 →", color = Color(0xFF4A2C10), fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Gestational Slider Bar
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF8B5F30).copy(alpha = 0.15f), RoundedCornerShape(16.dp))
                    .border(1.dp, Color(0xFF78350F).copy(alpha = 0.3f), RoundedCornerShape(16.dp))
                    .padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Semaine 1 (Nov '26)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4A2C10))
                    Surface(color = Color(0xFFFEF3C7), shape = RoundedCornerShape(4.dp)) {
                        Text("Semaine $selectedWeekNumber (${selectedWeekNumber} SA)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E), modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp))
                    }
                    Text("Semaine 40 (Juil '27)", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = Color(0xFF4A2C10))
                }

                Slider(
                    value = selectedWeekNumber.toFloat(),
                    onValueChange = { selectedWeekNumber = it.toInt() },
                    valueRange = 1f..40f,
                    colors = SliderDefaults.colors(
                        thumbColor = Color(0xFFD97706),
                        activeTrackColor = Color(0xFF92400E)
                    )
                )
            }

            // Clinical Folders / Tabs Section
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    TabButton("Calculateur FET (Room)", activeTab == 0) { activeTab = 0 }
                    TabButton("Chronologie RMA", activeTab == 1) { activeTab = 1 }
                    TabButton("Check-lists", activeTab == 2) { activeTab = 2 }
                    TabButton("Carnet Fatou", activeTab == 3) { activeTab = 3 }
                }

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFFFAF4E8),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp, topEnd = 16.dp),
                    border = BorderStroke(1.dp, Color(0xFFBFA278))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        when (activeTab) {
                            0 -> {
                                Text("Calculateur DPA & Suivi FET (Room Database)", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E1905))
                                Text("Données persistées localement via Room (J5 Euploïde Mâle).", fontSize = 11.sp, color = Color(0xFF78350F))
                                Divider(color = Color(0xFF78350F).copy(alpha = 0.2f))
                                Text("• Date du Transfert FET (Room) : $fetDateFormatted", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E))
                                Text("• DDR Médicale calculée : $lmpDateFormatted", fontSize = 11.sp, color = Color(0xFF3B2814))
                                Text("• Semaine Actuelle calculée : S${uiState.currentWeek} (${uiState.currentDaysInWeek} jours)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
                                Spacer(modifier = Modifier.height(4.dp))
                                Button(
                                    onClick = {
                                        // Reset to default Nov 5, 2026
                                        viewModel.updateFetDate(1793836800000L)
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF92400E)),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text("Réinitialiser au 05 Nov 2026", fontSize = 10.sp)
                                }
                            }
                            1 -> {
                                Text("Chronologie Obstétrique • RMA of New York", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E1905))
                                TimelineRow("S1-S3", "Préparation endomètre & Transfert J5", "Validé ✓")
                                TimelineRow("S6-S8", "Échographie de viabilité cardiaque (120-160 bpm)", "Validé ✓")
                                TimelineRow("S12", "Échographie T1 (Clarté nucale) & DPNI", "En cours ⏳")
                                TimelineRow("S22", "Échographie morphologique T2", "À venir")
                                TimelineRow("S40", "Date Prévue d'Accouchement (22/07/2027)", "Terme")
                            }
                            2 -> {
                                Text("Engagements Cliniques & Suivi Euploïde", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E1905))
                                ChecklistItem("Validation du protocole FET & confirmation garçon euploïde 46,XY.")
                                ChecklistItem("Transfert blastocyste J5 sous contrôle échographique.")
                                ChecklistItem("Bêta-hCG sériés confirmant une cinétique vigoureuse.")
                                ChecklistItem("Échographie du premier trimestre (Clarté nucale à S12).")
                            }
                            3 -> {
                                Text("Carnet Intime de Fatou", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E1905))
                                OutlinedTextField(
                                    value = journalText,
                                    onValueChange = { journalText = it },
                                    placeholder = { Text("Notez ici vos sensations et pensées pour votre petit garçon...", fontSize = 11.sp) },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(100.dp),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Color(0xFF92400E),
                                        unfocusedBorderColor = Color(0xFFBFA278),
                                        focusedContainerColor = Color.White,
                                        unfocusedContainerColor = Color.White
                                    )
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Stockage local Room actif", fontSize = 10.sp, color = Color(0xFF4A2C10))
                Text("RMA of New York • DPA 22/07/2027", fontSize = 10.sp, color = Color(0xFF4A2C10))
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color, CircleShape)
                .border(0.5.dp, Color(0xFF78350F), CircleShape)
        )
        Text(label, fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color(0xFF3B2814))
    }
}

@Composable
fun BiometricCard(title: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        color = Color(0xFF92400E).copy(alpha = 0.08f),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color(0xFF92400E).copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(title.uppercase(), fontSize = 9.sp, fontWeight = FontWeight.Bold, color = Color(0xFF78350F))
            Spacer(modifier = Modifier.height(2.dp))
            Text(value, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF2E1905), maxLines = 1)
        }
    }
}

@Composable
fun ClinicalSectionCard(title: String, content: String, onSpeak: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFBEB)),
        border = BorderStroke(1.dp, Color(0xFFD97706).copy(alpha = 0.2f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(title, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E))
                IconButton(onClick = onSpeak, modifier = Modifier.size(24.dp)) {
                    Icon(Icons.Default.VolumeUp, contentDescription = null, tint = Color(0xFF92400E), modifier = Modifier.size(16.dp))
                }
            }
            Text(content, fontSize = 11.sp, color = Color(0xFF3B2814), fontStyle = FontStyle.Italic)
        }
    }
}

@Composable
fun TabButton(title: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        color = if (isSelected) Color(0xFFFAF4E8) else Color(0xFF92400E).copy(alpha = 0.15f),
        shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp),
        border = BorderStroke(1.dp, if (isSelected) Color(0xFFBFA278) else Color.Transparent)
    ) {
        Text(
            text = title,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = if (isSelected) Color(0xFF2E1905) else Color(0xFF78350F),
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun TimelineRow(week: String, desc: String, status: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(week, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF92400E), modifier = Modifier.width(50.dp))
        Text(desc, fontSize = 11.sp, color = Color(0xFF3B2814), modifier = Modifier.weight(1f))
        Text(status, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF16A34A))
    }
}

@Composable
fun ChecklistItem(text: String) {
    var checked by remember { mutableStateOf(true) }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = { checked = it },
            colors = CheckboxDefaults.colors(checkedColor = Color(0xFF92400E))
        )
        Text(text, fontSize = 11.sp, color = Color(0xFF3B2814))
    }
}


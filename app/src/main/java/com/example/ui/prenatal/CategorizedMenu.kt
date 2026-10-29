package com.example.ui.prenatal

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

enum class MainCategory(
    val titleFr: String,
    val titleEn: String,
    val subtitleFr: String,
    val subtitleEn: String,
    val icon: ImageVector,
    val count: Int
) {
    GUIDE(
        titleFr = "Guide & Savoir",
        titleEn = "Guide & Knowledge",
        subtitleFr = "Conseils, Glossaire & Chronologie",
        subtitleEn = "Tips, Glossary & Timeline",
        icon = Icons.Default.MenuBook,
        count = 3
    ),
    CLINICAL(
        titleFr = "Suivi Clinique",
        titleEn = "Clinical Care",
        subtitleFr = "Coups, PIO, Poids & FET",
        subtitleEn = "Kicks, PIO, Weight & FET",
        icon = Icons.Default.MonitorHeart,
        count = 4
    ),
    MEMORIES(
        titleFr = "Mémoire",
        titleEn = "Memories & Prep",
        subtitleFr = "Échographies, Projet & Carnet",
        subtitleEn = "Scans, Birth Plan & Journal",
        icon = Icons.Default.Favorite,
        count = 3
    )
}

enum class PrenatalSection(
    val category: MainCategory,
    val titleFr: String,
    val titleEn: String,
    val icon: ImageVector,
    val testTag: String
) {
    // Category 1: Guide & Knowledge
    GUIDE_PRENATAL(
        category = MainCategory.GUIDE,
        titleFr = "Guide & Précautions",
        titleEn = "Prenatal Guide & Tips",
        icon = Icons.Default.HealthAndSafety,
        testTag = "menu_item_guide"
    ),
    GLOSSARY(
        category = MainCategory.GUIDE,
        titleFr = "Glossaire Médical",
        titleEn = "Medical Glossary",
        icon = Icons.Default.AutoStories,
        testTag = "menu_item_glossary"
    ),
    TIMELINE(
        category = MainCategory.GUIDE,
        titleFr = "Chronologie RMA",
        titleEn = "Obstetric Timeline",
        icon = Icons.Default.Timeline,
        testTag = "menu_item_timeline"
    ),

    // Category 2: Clinical Care
    KICK_COUNTER(
        category = MainCategory.CLINICAL,
        titleFr = "Compte des Coups",
        titleEn = "Kick Counter",
        icon = Icons.Default.TouchApp,
        testTag = "menu_item_kicks"
    ),
    PIO_MEDS(
        category = MainCategory.CLINICAL,
        titleFr = "Injections PIO & FIV",
        titleEn = "PIO & IVF Tracker",
        icon = Icons.Default.Vaccines,
        testTag = "menu_item_pio"
    ),
    WEIGHT_TRACKER(
        category = MainCategory.CLINICAL,
        titleFr = "Poids Maternel",
        titleEn = "Maternal Weight",
        icon = Icons.Default.MonitorWeight,
        testTag = "menu_item_weight"
    ),
    FET_CALCULATOR(
        category = MainCategory.CLINICAL,
        titleFr = "Calculateur FET (Room)",
        titleEn = "FET Calculator (Room)",
        icon = Icons.Default.CalendarMonth,
        testTag = "menu_item_fet"
    ),

    // Category 3: Memories & Prep
    ULTRASOUND_VAULT(
        category = MainCategory.MEMORIES,
        titleFr = "Galerie Échographies",
        titleEn = "Ultrasound Vault",
        icon = Icons.Default.PhotoLibrary,
        testTag = "menu_item_vault"
    ),
    BIRTH_PLAN(
        category = MainCategory.MEMORIES,
        titleFr = "Projet de Naissance",
        titleEn = "Birth Plan Builder",
        icon = Icons.Default.AssignmentTurnedIn,
        testTag = "menu_item_birth_plan"
    ),
    JOURNAL(
        category = MainCategory.MEMORIES,
        titleFr = "Carnet Intime & Prompts",
        titleEn = "Fatou's Journal & Prompts",
        icon = Icons.Default.EditNote,
        testTag = "menu_item_journal"
    )
}

@Composable
fun CategorizedMenu(
    selectedSection: PrenatalSection,
    onSelectSection: (PrenatalSection) -> Unit,
    isFrench: Boolean,
    modifier: Modifier = Modifier
) {
    val activeCategory = selectedSection.category

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("categorized_menu_container"),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Row 1: Primary Category Cards (with Icons, Titles & Subtitles)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MainCategory.values().forEach { cat ->
                val isCatActive = activeCategory == cat
                Surface(
                    onClick = {
                        val firstSection = PrenatalSection.values().first { it.category == cat }
                        onSelectSection(firstSection)
                    },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isCatActive) Color(0xFF92400E) else Color(0xFFFAF4E8),
                    border = BorderStroke(
                        width = if (isCatActive) 1.5.dp else 1.dp,
                        color = if (isCatActive) Color(0xFF92400E) else Color(0xFFBFA278)
                    ),
                    modifier = Modifier
                        .widthIn(min = 140.dp)
                        .testTag("category_tab_${cat.name.lowercase()}")
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isCatActive) Color.White.copy(alpha = 0.2f)
                                    else Color(0xFF92400E).copy(alpha = 0.12f)
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = cat.icon,
                                contentDescription = null,
                                tint = if (isCatActive) Color.White else Color(0xFF92400E),
                                modifier = Modifier.size(18.dp)
                            )
                        }

                        Column {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Text(
                                    text = if (isFrench) cat.titleFr else cat.titleEn,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCatActive) Color.White else Color(0xFF2E1905)
                                )
                                Surface(
                                    shape = CircleShape,
                                    color = if (isCatActive) Color.White.copy(alpha = 0.25f) else Color(0xFF92400E).copy(alpha = 0.15f)
                                ) {
                                    Text(
                                        text = "${cat.count}",
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isCatActive) Color.White else Color(0xFF92400E),
                                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 1.dp)
                                    )
                                }
                            }
                            Text(
                                text = if (isFrench) cat.subtitleFr else cat.subtitleEn,
                                fontSize = 9.sp,
                                color = if (isCatActive) Color.White.copy(alpha = 0.85f) else Color(0xFF78350F),
                                maxLines = 1
                            )
                        }
                    }
                }
            }
        }

        // Row 2: Sub-Menu with Dedicated Icons for the Active Category
        Surface(
            color = Color(0xFF92400E).copy(alpha = 0.05f),
            shape = RoundedCornerShape(10.dp),
            border = BorderStroke(1.dp, Color(0xFFBFA278).copy(alpha = 0.6f)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 6.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                PrenatalSection.values()
                    .filter { it.category == activeCategory }
                    .forEach { section ->
                        val isSelected = selectedSection == section
                        Surface(
                            onClick = { onSelectSection(section) },
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) Color(0xFF92400E) else Color(0xFFFAF4E8),
                            border = BorderStroke(
                                width = 1.dp,
                                color = if (isSelected) Color(0xFF92400E) else Color(0xFFBFA278)
                            ),
                            modifier = Modifier.testTag(section.testTag)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = section.icon,
                                    contentDescription = null,
                                    tint = if (isSelected) Color.White else Color(0xFF92400E),
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = if (isFrench) section.titleFr else section.titleEn,
                                    fontSize = 11.sp,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isSelected) Color.White else Color(0xFF3B2814)
                                )
                            }
                        }
                    }
            }
        }
    }
}

@Composable
fun StickyBottomTabBar(
    selectedSection: PrenatalSection,
    onSelectSection: (PrenatalSection) -> Unit,
    isFrench: Boolean,
    modifier: Modifier = Modifier
) {
    val activeCategory = selectedSection.category

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(9999f)
            .shadow(24.dp)
            .testTag("sticky_bottom_tab_bar"),
        color = Color(0xFFFAF4E8).copy(alpha = 0.95f),
        border = BorderStroke(1.dp, Color(0xFFBFA278).copy(alpha = 0.6f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MainCategory.values().forEach { cat ->
                val isCatActive = activeCategory == cat
                val tabColor = if (isCatActive) Color(0xFF92400E) else Color(0xFF78350F).copy(alpha = 0.8f)

                Surface(
                    onClick = {
                        val firstSection = PrenatalSection.values().first { it.category == cat }
                        onSelectSection(firstSection)
                    },
                    shape = RoundedCornerShape(12.dp),
                    color = if (isCatActive) Color(0xFF92400E).copy(alpha = 0.15f) else Color.Transparent,
                    modifier = Modifier.testTag("sticky_tab_${cat.name.lowercase()}")
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(2.dp)
                    ) {
                        Icon(
                            imageVector = cat.icon,
                            contentDescription = null,
                            tint = tabColor,
                            modifier = Modifier.size(22.dp)
                        )
                        Text(
                            text = if (isFrench) cat.titleFr else cat.titleEn,
                            fontSize = 11.sp,
                            fontWeight = if (isCatActive) FontWeight.Bold else FontWeight.Medium,
                            color = tabColor,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CleanMobileNavMenu(
    isOpen: Boolean,
    onDismiss: () -> Unit,
    selectedSection: PrenatalSection,
    onSelectSection: (PrenatalSection) -> Unit,
    isFrench: Boolean
) {
    if (isOpen) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(99999f)
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable { onDismiss() }
        ) {
            androidx.compose.animation.AnimatedVisibility(
                visible = isOpen,
                enter = androidx.compose.animation.slideInVertically(initialOffsetY = { it }) + androidx.compose.animation.fadeIn(),
                exit = androidx.compose.animation.slideOutVertically(targetOffsetY = { it }) + androidx.compose.animation.fadeOut(),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 600.dp)
                        .clickable(enabled = false) {},
                    color = Color(0xFFFAF4E8),
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
                    border = BorderStroke(1.dp, Color(0xFFBFA278)),
                    shadowElevation = 16.dp
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
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
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = null,
                                    tint = Color(0xFF92400E),
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = if (isFrench) "Menu de Navigation" else "Navigation Menu",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF2E1905)
                                )
                            }
                            IconButton(
                                onClick = onDismiss,
                                modifier = Modifier
                                    .size(48.dp)
                                    .testTag("close_nav_drawer_button")
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = if (isFrench) "Fermer le menu" else "Close menu",
                                    tint = Color(0xFF78350F)
                                )
                            }
                        }

                        Divider(color = Color(0xFFBFA278).copy(alpha = 0.4f))

                        MainCategory.values().forEach { cat ->
                            val isCatActive = selectedSection.category == cat
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        if (isCatActive) Color(0xFF92400E).copy(alpha = 0.08f) else Color.Transparent,
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(8.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = cat.icon,
                                        contentDescription = null,
                                        tint = Color(0xFF92400E),
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Text(
                                        text = if (isFrench) cat.titleFr else cat.titleEn,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF92400E)
                                    )
                                }

                                PrenatalSection.values()
                                    .filter { it.category == cat }
                                    .forEach { section ->
                                        val isSelected = selectedSection == section
                                        Surface(
                                            onClick = {
                                                onSelectSection(section)
                                                onDismiss()
                                            },
                                            shape = RoundedCornerShape(8.dp),
                                            color = if (isSelected) Color(0xFF92400E) else Color(0xFFFAF4E8),
                                            border = BorderStroke(1.dp, if (isSelected) Color(0xFF92400E) else Color(0xFFBFA278).copy(alpha = 0.5f)),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .heightIn(min = 48.dp)
                                                .testTag("nav_item_${section.name.lowercase()}")
                                        ) {
                                            Row(
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(horizontal = 12.dp, vertical = 10.dp),
                                                verticalAlignment = Alignment.CenterVertically,
                                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                                            ) {
                                                Icon(
                                                    imageVector = section.icon,
                                                    contentDescription = null,
                                                    tint = if (isSelected) Color.White else Color(0xFF92400E),
                                                    modifier = Modifier.size(20.dp)
                                                )
                                                Text(
                                                    text = if (isFrench) section.titleFr else section.titleEn,
                                                    fontSize = 12.sp,
                                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                                    color = if (isSelected) Color.White else Color(0xFF2E1905)
                                                )
                                            }
                                        }
                                    }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TopQuickAccessBar(
    selectedSection: PrenatalSection,
    onSelectSection: (PrenatalSection) -> Unit,
    isFrench: Boolean,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .testTag("top_quick_access_bar"),
        color = Color(0xFFFAF4E8),
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.dp, Color(0xFFBFA278))
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
                Text(
                    text = if (isFrench) "🚀 Accès Rapide aux Pages & Rubriques" else "🚀 Quick Access to Pages & Sections",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF92400E)
                )
                Text(
                    text = if (isFrench) "Touchez pour ouvrir" else "Tap to open",
                    fontSize = 10.sp,
                    color = Color(0xFF78350F)
                )
            }

            // Chunk sections into rows of 3 so everything fits on screen without horizontal scrolling
            val rows = PrenatalSection.values().toList().chunked(3)
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                rows.forEach { rowSections ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        rowSections.forEach { section ->
                            val isSelected = selectedSection == section
                            val categoryColor = when (section.category) {
                                MainCategory.GUIDE -> Color(0xFF92400E)
                                MainCategory.CLINICAL -> Color(0xFF15803D)
                                MainCategory.MEMORIES -> Color(0xFFB91C1C)
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = if (isSelected) categoryColor else categoryColor.copy(alpha = 0.12f),
                                border = BorderStroke(1.dp, categoryColor.copy(alpha = 0.5f)),
                                modifier = Modifier
                                    .weight(1f)
                                    .heightIn(min = 36.dp)
                                    .clickable { onSelectSection(section) }
                                    .testTag("top_chip_${section.name.lowercase()}")
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 4.dp, vertical = 6.dp),
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        imageVector = section.icon,
                                        contentDescription = null,
                                        tint = if (isSelected) Color.White else categoryColor,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = if (isFrench) section.titleFr else section.titleEn,
                                        fontSize = 10.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                        color = if (isSelected) Color.White else Color(0xFF2E1905),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis
                                    )
                                }
                            }
                        }
                        repeat(3 - rowSections.size) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}




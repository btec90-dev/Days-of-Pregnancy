package com.example.ui.prenatal

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage

data class ScanVaultItem(
    val id: String,
    val titleFr: String,
    val titleEn: String,
    val weekText: String,
    val descriptionFr: String,
    val descriptionEn: String,
    val localUri: Uri? = null
)

@Composable
fun UltrasoundVaultSection(isFrench: Boolean) {
    var vaultItems by remember {
        mutableStateOf(
            listOf(
                ScanVaultItem(
                    "scan_s6",
                    "Sac Gestationnel & Vésicule Vitelline",
                    "Yolk Sac & Gestational Sac",
                    "S6 (RMA of New York)",
                    "Confirmation de l'implantation intra-utérine et première activité cardiaque embryonnaire.",
                    "Intrauterine implantation confirmed with initial embryonic cardiac activity."
                ),
                ScanVaultItem(
                    "scan_s12",
                    "Échographie T1 (Clarté Nucale)",
                    "12-Week Nuchal Translucency Scan",
                    "S12 (Paris / RMA)",
                    "Mesure de la clarté nucale (1.2 mm), os propre du nez visualisé et DPNI rassurant.",
                    "Normal nuchal translucency (1.2 mm), nasal bone visualized, reassuring NIPT screen."
                ),
                ScanVaultItem(
                    "scan_s22",
                    "Échographie Morphologique T2",
                    "22-Week Detailed Anatomy Scan",
                    "S22 (À venir)",
                    "Analyse anatomique détaillée de tous les organes de votre petit garçon (46, XY).",
                    "Detailed anatomical organ survey for baby boy (46, XY euploid male)."
                ),
                ScanVaultItem(
                    "scan_bump",
                    "Photo du Ventre Rond de Fatou",
                    "Fatou's Pregnancy Bump Photo",
                    "Maternité",
                    "Souvenir précieux de la silhouette grandissante qui abrite votre futur trésor.",
                    "Precious bump milestone snapshot documenting maternal growth."
                )
            )
        )
    }

    var activeScanId by remember { mutableStateOf<String?>(null) }

    // Android Zero-Permission Photo Picker
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null && activeScanId != null) {
            vaultItems = vaultItems.map { item ->
                if (item.id == activeScanId) item.copy(localUri = uri) else item
            }
        }
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
                        imageVector = Icons.Default.Collections,
                        contentDescription = null,
                        tint = Color(0xFF92400E),
                        modifier = Modifier.size(22.dp)
                    )
                    Column {
                        Text(
                            text = if (isFrench) "Galerie & Coffre-Fort Échographies" else "Ultrasound & Bump Photo Vault",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2E1905)
                        )
                        Text(
                            text = if (isFrench) "Vos clichés et clichés d'échographies protégés localement" else "Locally preserved ultrasound scans and bump photos",
                            fontSize = 10.sp,
                            color = Color(0xFF78350F)
                        )
                    }
                }

                Surface(
                    color = Color(0xFF92400E).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = if (isFrench) "Stockage Privé" else "Private Vault",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF78350F),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                    )
                }
            }

            Text(
                text = if (isFrench)
                    "Importez vos clichés réels (échographies RMA, échographies de ville et photos de ventre) pour enrichir le carnet de votre petit garçon."
                else
                    "Upload and preserve your real scans (RMA early sonograms, anatomy scans, and bump photos) securely on device.",
                fontSize = 10.sp,
                color = Color(0xFF593210)
            )

            Divider(color = Color(0xFFBFA278).copy(alpha = 0.3f))

            // Scan Cards Grid
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                vaultItems.forEach { item ->
                    Surface(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, Color(0xFFBFA278).copy(alpha = 0.6f)),
                        modifier = Modifier.fillMaxWidth()
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
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = if (isFrench) item.titleFr else item.titleEn,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2E1905)
                                    )
                                    Text(
                                        text = item.weekText,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Medium,
                                        color = Color(0xFF92400E)
                                    )
                                }

                                Button(
                                    onClick = {
                                        activeScanId = item.id
                                        photoPickerLauncher.launch(
                                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                        )
                                    },
                                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF92400E)),
                                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AddPhotoAlternate,
                                        contentDescription = null,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = if (item.localUri == null)
                                            (if (isFrench) "Importer Cliché" else "Upload Scan")
                                        else
                                            (if (isFrench) "Modifier Cliché" else "Replace Scan"),
                                        fontSize = 10.sp
                                    )
                                }
                            }

                            if (item.localUri != null) {
                                AsyncImage(
                                    model = item.localUri,
                                    contentDescription = item.titleFr,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .border(1.dp, Color(0xFFBFA278), RoundedCornerShape(8.dp))
                                )
                            } else {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(70.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFFAF5ED))
                                        .border(1.dp, Color(0xFFD97706).copy(alpha = 0.25f), RoundedCornerShape(8.dp))
                                        .clickable {
                                            activeScanId = item.id
                                            photoPickerLauncher.launch(
                                                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                            )
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Image,
                                            contentDescription = null,
                                            tint = Color(0xFF92400E),
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Text(
                                            text = if (isFrench) "Emplacement vide • Touchez pour ajouter une photo" else "Empty slot • Tap to select an image from gallery",
                                            fontSize = 11.sp,
                                            color = Color(0xFF78350F)
                                        )
                                    }
                                }
                            }

                            Text(
                                text = if (isFrench) item.descriptionFr else item.descriptionEn,
                                fontSize = 10.sp,
                                color = Color(0xFF3B2814)
                            )
                        }
                    }
                }
            }
        }
    }
}

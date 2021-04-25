package com.example.emptycompose

data class HomeCardData(
    val title: String,
    val price: Int,
    val vendor: Vendor,
    val photo: SamplePhoto,
)

enum class Vendor {
    Alphi,
    Lmb,
    Mal,
    Six,
    Squiggle,
}

enum class SamplePhoto {
    Bamboo,
    Dusty,
    Flow,
    High,
    Hopscotch,
    Ok,
}

fun getVendorResId(vendor: Vendor): Int {
    when(vendor) {
        Vendor.Alphi -> {
            return R.drawable.ic_alphi_logo
        }
        Vendor.Lmb -> {
            return R.drawable.ic_lmb_logo
        }
        Vendor.Mal -> {
            return R.drawable.ic_mal_logo
        }
        Vendor.Six -> {
            return R.drawable.ic_6_logo
        }
        else -> {
            return R.drawable.ic_squiggle_logo
        }
    }
}

fun getPhotoResId(photo: SamplePhoto): Int {
    when(photo) {
        SamplePhoto.Bamboo -> {
            return R.drawable.photo_bamboo
        }
        SamplePhoto.Dusty -> {
            return R.drawable.photo_dusty
        }
        SamplePhoto.Flow -> {
            return R.drawable.photo_flow
        }
        SamplePhoto.High -> {
            return R.drawable.photo_high
        }
        SamplePhoto.Hopscotch -> {
            return R.drawable.photo_hopscotch
        }
        else -> {
            return R.drawable.photo_ok
        }
    }
}

val SampleItemsData = listOf(
    HomeCardData(
        title = "High Tea Cups",
        price = 36,
        vendor = Vendor.Six,
        photo = SamplePhoto.High
    ),
    HomeCardData(
        title = "Hopscotch Shoes",
        price = 134,
        vendor = Vendor.Mal,
        photo = SamplePhoto.Hopscotch
    ),
    HomeCardData(
        title = "Dusty Pink Satchel",
        price = 133,
        vendor = Vendor.Squiggle,
        photo = SamplePhoto.Dusty
    ),
    HomeCardData(
        title = "OK Glow Lamp",
        price = 20,
        vendor = Vendor.Alphi,
        photo = SamplePhoto.Ok
    ),
    HomeCardData(
        title = "Bamboo Turntables",
        price = 133,
        vendor = Vendor.Squiggle,
        photo = SamplePhoto.Bamboo
    ),
    HomeCardData(
        title = "Flow Shirt Blouse",
        price = 240,
        vendor = Vendor.Lmb,
        photo = SamplePhoto.Flow
    ),
)
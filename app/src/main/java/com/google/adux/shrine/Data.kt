package com.google.adux.shrine

data class HomeCardData(
    val id: Int,
    val title: String,
    val price: Int,
    val vendor: Vendor,
    val category: Category,
    val photo: Int,
)

enum class Vendor {
    Alphi,
    Lmb,
    Mal,
    Six,
    Squiggle,
}

enum class Category {
    Accessories,
    Clothing,
    Home
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

val SampleItemsData = listOf(
    HomeCardData(
        id = 0,
        title = "High Tea Cups",
        price = 36,
        vendor = Vendor.Six,
        category = Category.Accessories,
        photo = R.drawable.photo_0
    ),
    HomeCardData(
        id = 1,
        title = "Hopscotch Shoes",
        price = 134,
        vendor = Vendor.Mal,
        category = Category.Accessories,
        photo = R.drawable.photo_1
    ),
    HomeCardData(
        id = 2,
        title = "Dusty Pink Satchel",
        price = 133,
        vendor = Vendor.Squiggle,
        category = Category.Accessories,
        photo = R.drawable.photo_2
    ),
    HomeCardData(
        id = 3,
        title = "OK Glow Lamp",
        price = 20,
        vendor = Vendor.Alphi,
        category = Category.Accessories,
        photo = R.drawable.photo_3
    ),
    HomeCardData(
        id = 4,
        title = "Bamboo Turntables",
        price = 133,
        vendor = Vendor.Squiggle,
        category = Category.Accessories,
        photo = R.drawable.photo_4
    ),
    HomeCardData(
        id = 5,
        title = "Flow Shirt Blouse",
        price = 240,
        vendor = Vendor.Lmb,
        category = Category.Accessories,
        photo = R.drawable.photo_5
    ),
)
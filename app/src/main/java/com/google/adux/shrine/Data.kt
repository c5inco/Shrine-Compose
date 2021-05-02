package com.google.adux.shrine

data class ItemData(
    val id: Int,
    val title: String,
    val price: Int,
    val vendor: Vendor,
    val category: Category,
    val photoResId: Int,
)

enum class Vendor {
    Alphi,
    Lmbrjk,
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
        Vendor.Lmbrjk -> {
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
    ItemData(
        id = 0,
        title = "High Tea Cups",
        price = 36,
        vendor = Vendor.Six,
        category = Category.Accessories,
        photoResId = R.drawable.photo_0
    ),
    ItemData(
        id = 1,
        title = "Hopscotch Shoes",
        price = 134,
        vendor = Vendor.Mal,
        category = Category.Accessories,
        photoResId = R.drawable.photo_1
    ),
    ItemData(
        id = 2,
        title = "Dusty Pink Satchel",
        price = 133,
        vendor = Vendor.Squiggle,
        category = Category.Accessories,
        photoResId = R.drawable.photo_2
    ),
    ItemData(
        id = 3,
        title = "OK Glow Lamp",
        price = 20,
        vendor = Vendor.Alphi,
        category = Category.Accessories,
        photoResId = R.drawable.photo_3
    ),
    ItemData(
        id = 4,
        title = "Bamboo Turntables",
        price = 133,
        vendor = Vendor.Squiggle,
        category = Category.Accessories,
        photoResId = R.drawable.photo_4
    ),
    ItemData(
        id = 5,
        title = "Flow Shirt Blouse",
        price = 240,
        vendor = Vendor.Lmbrjk,
        category = Category.Accessories,
        photoResId = R.drawable.photo_5
    ),
    ItemData(
        id = 6,
        title = "Flow Shirt Blouse",
        price = 240,
        vendor = Vendor.Lmbrjk,
        category = Category.Accessories,
        photoResId = R.drawable.photo_6
    ),
    ItemData(
        id = 7,
        title = "Flow Shirt Blouse",
        price = 240,
        vendor = Vendor.Lmbrjk,
        category = Category.Accessories,
        photoResId = R.drawable.photo_7
    ),
    ItemData(
        id = 8,
        title = "Flow Shirt Blouse",
        price = 240,
        vendor = Vendor.Lmbrjk,
        category = Category.Accessories,
        photoResId = R.drawable.photo_8
    ),
    ItemData(
        id = 9,
        title = "Flow Shirt Blouse",
        price = 240,
        vendor = Vendor.Lmbrjk,
        category = Category.Accessories,
        photoResId = R.drawable.photo_8
    ),
)
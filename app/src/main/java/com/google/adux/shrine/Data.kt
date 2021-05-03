package com.google.adux.shrine

data class ItemData(
    val id: Int,
    val title: String,
    val price: Int,
    val vendor: Vendor,
    val category: Category,
    val isFeatured: Boolean = false,
    val photoResId: Int,
    val photoOrientation: PhotoOrientation = PhotoOrientation.Landscape
)

enum class Vendor {
    Alphi,
    Lmbrjk,
    Mal,
    Six,
    Squiggle,
}

enum class Category {
    All,
    Accessories,
    Clothing,
    Home
}

enum class PhotoOrientation {
    Portrait,
    Landscape
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
        title = "Vagabond sack",
        price = 120,
        vendor = Vendor.Squiggle,
        category = Category.Accessories,
        isFeatured = true,
        photoResId = R.drawable.photo_0
    ),
    ItemData(
        id = 1,
        title = "Stella sunglasses",
        price = 58,
        vendor = Vendor.Mal,
        category = Category.Accessories,
        isFeatured = true,
        photoResId = R.drawable.photo_1
    ),
    ItemData(
        id = 2,
        title = "Whitney belt",
        price = 35,
        vendor = Vendor.Lmbrjk,
        category = Category.Accessories,
        photoResId = R.drawable.photo_2
    ),
    ItemData(
        id = 3,
        title = "Garden stand",
        price = 98,
        vendor = Vendor.Alphi,
        category = Category.Accessories,
        isFeatured = true,
        photoResId = R.drawable.photo_3
    ),
    ItemData(
        id = 4,
        title = "Strut earrings",
        price = 34,
        vendor = Vendor.Six,
        category = Category.Accessories,
        photoResId = R.drawable.photo_4
    ),
    ItemData(
        id = 5,
        title = "Varsity socks",
        price = 12,
        vendor = Vendor.Lmbrjk,
        category = Category.Accessories,
        photoResId = R.drawable.photo_5
    ),
    ItemData(
        id = 6,
        title = "Weave key ring",
        price = 16,
        vendor = Vendor.Six,
        category = Category.Accessories,
        photoResId = R.drawable.photo_6
    ),
    ItemData(
        id = 7,
        title = "Gatsby hat",
        price = 40,
        vendor = Vendor.Six,
        category = Category.Accessories,
        isFeatured = true,
        photoResId = R.drawable.photo_7
    ),
    ItemData(
        id = 8,
        title = "Shrug bag",
        price = 198,
        vendor = Vendor.Squiggle,
        category = Category.Accessories,
        isFeatured = true,
        photoResId = R.drawable.photo_8
    ),
    ItemData(
        id = 9,
        title = "Gilt desk trio",
        price = 58,
        vendor = Vendor.Alphi,
        category = Category.Home,
        isFeatured = true,
        photoResId = R.drawable.photo_9
    ),
    ItemData(
        id = 10,
        title = "Copper wire rack",
        price = 18,
        vendor = Vendor.Alphi,
        category = Category.Home,
        photoResId = R.drawable.photo_10
    ),
    ItemData(
        id = 11,
        title = "Soothe ceramic set",
        price = 28,
        vendor = Vendor.Mal,
        category = Category.Home,
        photoResId = R.drawable.photo_11
    ),
    ItemData(
        id = 12,
        title = "Hurrahs tea set",
        price = 34,
        vendor = Vendor.Six,
        category = Category.Home,
        photoResId = R.drawable.photo_12
    ),
    ItemData(
        id = 13,
        title = "Blue stone mug",
        price = 18,
        vendor = Vendor.Mal,
        category = Category.Home,
        isFeatured = true,
        photoResId = R.drawable.photo_13
    ),
    ItemData(
        id = 14,
        title = "Rainwater tray",
        price = 27,
        vendor = Vendor.Six,
        category = Category.Home,
        isFeatured = true,
        photoResId = R.drawable.photo_14
    ),
    ItemData(
        id = 15,
        title = "Chambray napkins",
        price = 16,
        vendor = Vendor.Six,
        category = Category.Home,
        isFeatured = true,
        photoResId = R.drawable.photo_15
    ),
    ItemData(
        id = 16,
        title = "Succulent planters",
        price = 16,
        vendor = Vendor.Alphi,
        category = Category.Home,
        isFeatured = true,
        photoResId = R.drawable.photo_16
    ),
    ItemData(
        id = 17,
        title = "Quartet table",
        price = 175,
        vendor = Vendor.Squiggle,
        category = Category.Home,
        photoResId = R.drawable.photo_17
    ),
    ItemData(
        id = 18,
        title = "Kitchen quattro",
        price = 129,
        vendor = Vendor.Alphi,
        category = Category.Home,
        isFeatured = true,
        photoResId = R.drawable.photo_18
    ),
    ItemData(
        id = 19,
        title = "Clay sweater",
        price = 48,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        photoResId = R.drawable.photo_19
    ),
    ItemData(
        id = 20,
        title = "Sea tunic",
        price = 45,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        photoResId = R.drawable.photo_20
    ),
    ItemData(
        id = 21,
        title = "Plaster tunic",
        price = 38,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        photoResId = R.drawable.photo_21,
        photoOrientation = PhotoOrientation.Portrait
    ),
    ItemData(
        id = 22,
        title = "White pinstripe shirt",
        price = 70,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        photoResId = R.drawable.photo_22,
        photoOrientation = PhotoOrientation.Portrait
    ),
    ItemData(
        id = 23,
        title = "Chambray shirt",
        price = 70,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        photoResId = R.drawable.photo_23
    ),
    ItemData(
        id = 24,
        title = "Seabreeze sweater",
        price = 60,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        isFeatured = true,
        photoResId = R.drawable.photo_24,
        photoOrientation = PhotoOrientation.Portrait
    ),
    ItemData(
        id = 25,
        title = "Gentry jacket",
        price = 178,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        photoResId = R.drawable.photo_25
    ),
    ItemData(
        id = 26,
        title = "Navy trousers",
        price = 74,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        photoResId = R.drawable.photo_26,
        photoOrientation = PhotoOrientation.Portrait
    ),
    ItemData(
        id = 27,
        title = "Walter henley white",
        price = 38,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        isFeatured = true,
        photoResId = R.drawable.photo_27,
        photoOrientation = PhotoOrientation.Portrait
    ),
    ItemData(
        id = 28,
        title = "Surf and perf shirt",
        price = 48,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        isFeatured = true,
        photoResId = R.drawable.photo_28
    ),
    ItemData(
        id = 29,
        title = "Ginger scarf",
        price = 98,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        isFeatured = true,
        photoResId = R.drawable.photo_29,
        photoOrientation = PhotoOrientation.Portrait
    ),
    ItemData(
        id = 30,
        title = "Ramona crossover",
        price = 68,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        isFeatured = true,
        photoResId = R.drawable.photo_30,
        photoOrientation = PhotoOrientation.Portrait
    ),
    ItemData(
        id = 31,
        title = "Chambray shirt",
        price = 38,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        photoResId = R.drawable.photo_31
    ),
    ItemData(
        id = 32,
        title = "Class white collar",
        price = 58,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        photoResId = R.drawable.photo_32,
        photoOrientation = PhotoOrientation.Portrait
    ),
    ItemData(
        id = 33,
        title = "Cerise scallop tee",
        price = 42,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        isFeatured = true,
        photoResId = R.drawable.photo_33
    ),
    ItemData(
        id = 34,
        title = "Shoulder rolls tee",
        price = 27,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        photoResId = R.drawable.photo_34,
        photoOrientation = PhotoOrientation.Portrait
    ),
    ItemData(
        id = 35,
        title = "Grey slouch tank",
        price = 24,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        photoResId = R.drawable.photo_35,
        photoOrientation = PhotoOrientation.Portrait
    ),
    ItemData(
        id = 36,
        title = "Sunshirt dress",
        price = 58,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        photoResId = R.drawable.photo_36,
        photoOrientation = PhotoOrientation.Portrait
    ),
    ItemData(
        id = 37,
        title = "Fine lines tee",
        price = 58,
        vendor = Vendor.Lmbrjk,
        category = Category.Clothing,
        isFeatured = true,
        photoResId = R.drawable.photo_37,
        photoOrientation = PhotoOrientation.Portrait
    ),
)
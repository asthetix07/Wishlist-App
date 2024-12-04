package com.example.wishlistapp.data

import kotlinx.coroutines.flow.Flow

class WishRepository(private val wishDao: WishDao) {

    suspend fun addAWish(wish: Wish) {
        wishDao.addAWish(wish)
    }

    fun getWishes(): Flow<List<Wish>> = wishDao.getAllWishes()

    fun getAWishById(id: Long): Flow<Wish> {
        return wishDao.getAWishById(id) //must use Return key to return the flow, otherwise leads to error in the add_edit_detail_view page (as in "collectAsState()")
    }

    suspend fun updateAWish(wish: Wish) {
        wishDao.updateAWish(wish)
    }

    suspend fun deleteAWish(wish: Wish) {
        wishDao.deleteAWish(wish)
    }
}

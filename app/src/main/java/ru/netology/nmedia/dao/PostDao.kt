package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.entity.PostEntity

@Dao
interface PostDao {
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query("UPDATE PostEntity SET content = :content WHERE id = :id")
    fun update(id: Long, content: String)

    fun save(post: PostEntity) =
        if (post.id == 0L) insert(post) else update(post.id, post.content)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun remove(id: Long)

    @Query(
        """
        UPDATE PostEntity SET
        likes = likes + CASE WHEN liked THEN -1 ELSE 1 END,
        liked = CASE WHEN liked THEN 0 ELSE 1 END
        WHERE id = :id
        """
    )
    fun like(id: Long)

    @Query(
        """
        UPDATE PostEntity SET
        shares = shares + 1
        WHERE id = :id
        """
    )
    fun share(id: Long)
}

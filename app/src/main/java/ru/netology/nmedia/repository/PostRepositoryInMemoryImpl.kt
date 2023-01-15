package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var postsInitial = listOf(
        Post(
            id = 0,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Освоение новой профессии — это не только открывающиеся возможности и перспективы, но и настоящий вызов самому себе. Приходится выходить из зоны комфорта и перестраивать привычный образ жизни: менять распорядок дня, искать время для занятий, быть готовым к возможным неудачам в начале пути. В блоге рассказали, как избежать стресса на курсах профпереподготовки → http://netolo.gy/fPD",
            published = "23 сентября в 10:12",
            liked = false,
            likes = 0,
            shares = 0,
            views = 0,
            videoUrl = null
        ),
        Post(
            id = 0,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Делиться впечатлениями о любимых фильмах легко, а что если рассказать так, чтобы все заскучали \uD83D\uDE34\n",
            published = "22 сентября в 10:14",
            liked = false,
            likes = 0,
            shares = 0,
            views = 0,
            videoUrl = null
        ),
        Post(
            id = 0,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Таймбоксинг — отличный способ навести порядок в своём календаре и разобраться с делами, которые долго откладывали на потом. Его главный принцип — на каждое дело заранее выделяется определённый отрезок времени. В это время вы работаете только над одной задачей, не переключаясь на другие. Собрали советы, которые помогут внедрить таймбоксинг \uD83D\uDC47\uD83C\uDFFB",
            published = "22 сентября в 10:12",
            liked = false,
            likes = 0,
            shares = 0,
            views = 0,
            videoUrl = null
        ),
        Post(
            id = 0,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "\uD83D\uDE80 24 сентября стартует новый поток бесплатного курса «Диджитал-старт: первый шаг к востребованной профессии» — за две недели вы попробуете себя в разных профессиях и определите, что подходит именно вам → http://netolo.gy/fQ",
            published = "21 сентября в 10:12",
            liked = false,
            likes = 0,
            shares = 0,
            views = 0,
            videoUrl = null
        ),
        Post(
            id = 0,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Диджитал давно стал частью нашей жизни: мы общаемся в социальных сетях и мессенджерах, заказываем еду, такси и оплачиваем счета через приложения.",
            published = "20 сентября в 10:14",
            liked = false,
            likes = 999_999,
            shares = 0,
            views = 0,
            videoUrl = null
        ),
        Post(
            id = 0,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Большая афиша мероприятий осени: конференции, выставки и хакатоны для жителей Москвы, Ульяновска и Новосибирска \uD83D\uDE09",
            published = "19 сентября в 14:12",
            liked = false,
            likes = 9_999,
            shares = 0,
            views = 0,
            videoUrl = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
        ),
        Post(
            id = 0,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Языков программирования много, и выбрать какой-то один бывает нелегко. Собрали подборку статей, которая поможет вам начать, если вы остановили свой выбор на JavaScript.",
            published = "19 сентября в 10:24",
            liked = true,
            likes = 1,
            shares = 0,
            views = 0,
            videoUrl = null
        ),
        Post(
            id = 0,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Знаний хватит на всех: на следующей неделе разбираемся с разработкой мобильных приложений, учимся рассказывать истории и составлять PR-стратегию прямо на бесплатных занятиях \uD83D\uDC47",
            published = "18 сентября в 10:12",
            liked = false,
            likes = 0,
            shares = 0,
            views = 0,
            videoUrl = "https://www.youtube.com/watch?v=t_S_cN2re4g"
        ),
        Post(
            id = 0,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            liked = false,
            likes = 999,
            shares = 5_099,
            views = 999,
            videoUrl = null
        ),
    )

    init {
        PostInternal.actualizeNextId()
        postsInitial.forEach {
            PostInternal.add(it)
        }
    }

    override fun getAll(): LiveData<List<Post>> = PostInternal.data

    override fun save(post: Post) {
        PostInternal.update(post)
    }

    override fun remove(id: Long) {
        PostInternal.remove(id)
    }

    override fun like(id: Long) {
        PostInternal.like(id)
    }

    override fun share(id: Long) {
        PostInternal.share(id)
    }
}

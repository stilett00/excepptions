object WallService {
    private var posts = emptyArray<Post>()
    private var comments = emptyArray<Comment>()
    private var nextID = 1

    fun add(post: Post): Post {
        val newPost = post.copy(id = nextID++)
        posts += newPost
        return posts.last()
    }

    fun printPosts() {
        posts.forEach { post ->
            println("Post ID: ${post.id}, Text: ${post.text}")
            post.attachments.forEach { attachment ->
                println("  Attachment type: ${attachment.type}")
                when (attachment) {
                    is AttachmentPhoto -> println("    Photo: ${attachment.photo}")
                    is AttachmentAudio -> println("    Audio: ${attachment.audio}")
                    is AttachmentVideo -> println("    Video: ${attachment.video}")
                }
            }
        }
    }

    fun createComment(postId: Int, comment: Comment): Comment {
        for (post in posts) {
            if (post.id == postId) {
                val newComment = comment.copy()
                comments += newComment
                return comments.last()
            }
        }
        throw PostNotFoundException("Пост с ID $postId не найден")
    }

    fun update(post: Post): Boolean {
        for ((index, existingPost) in posts.withIndex()) {
            if (existingPost.id == post.id) {
                posts[index] = post.copy(
                    reposts = post.reposts?.copy(),
                    attachments = if (post.attachments.isEmpty()) existingPost.attachments else post.attachments
                )
                return true
            }
        }
        return false
    }

    fun clear() {
        posts = emptyArray()
        nextID = 1
    }
}
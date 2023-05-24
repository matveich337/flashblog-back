package com.flasblog.back.service.interfaces;

import com.flasblog.back.model.CommentaryModel;
import com.flasblog.back.model.PostModel;
import java.util.List;

/** PostService. */
public interface PostInterface {
  PostModel getPostById(String postId);

  List<PostModel> getAllPosts();

  PostModel createPost(PostModel postModel);

  CommentaryModel createPostCommentary(CommentaryModel commentaryModel);
}

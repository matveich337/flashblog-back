package com.flasblog.back.service.implementations;

import com.flasblog.back.mapper.Mapper;
import com.flasblog.back.model.CommentaryModel;
import com.flasblog.back.model.PostModel;
import com.flasblog.back.persistence.entity.CommentaryEntity;
import com.flasblog.back.persistence.entity.PostEntity;
import com.flasblog.back.persistence.repository.CommentaryRepository;
import com.flasblog.back.persistence.repository.PostRepository;
import com.flasblog.back.service.interfaces.PostInterface;
import com.flasblog.back.service.interfaces.UserInterface;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/** PostServiceImpl. */
@Service
@AllArgsConstructor
public class PostImpl implements PostInterface {
  private PostRepository postRepository;
  private CommentaryRepository commentaryRepository;
  private UserInterface userInterface;

  @Override
  public PostModel getPostById(String postId) {
    return Mapper.I.postEntityToModel(
        postRepository.findById(postId).orElse(new PostEntity()));
  }

  @Override
  public List<PostModel> getAllPosts() {
    return Mapper.I.postEntitiesToModels(postRepository.findAll());
  }

  @Override
  public PostModel createPost(PostModel postModel) {
    PostEntity postEntity =
        PostEntity.builder()
            .creatorId(userInterface.getAuthenticatedUser().getId())
            .theme(postModel.getTheme())
            .content(postModel.getContent())
            .createdOn(LocalDateTime.now())
            .build();
    return Mapper.I.postEntityToModel(postRepository.save(postEntity));
  }

  @Override
  public CommentaryModel createPostCommentary(CommentaryModel commentaryModel) {
    CommentaryEntity commentaryEntity =
        Mapper.I.commentaryModelToEntity(commentaryModel);
    commentaryEntity.setCreatorId(userInterface.getAuthenticatedUser().getId());
    commentaryEntity.setCreatedOn(LocalDateTime.now());
    return Mapper.I.commentaryEntityToModel(
        commentaryRepository.save(commentaryEntity));
  }
}

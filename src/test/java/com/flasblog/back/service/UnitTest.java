package com.flasblog.back.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.flasblog.back.model.CommentaryModel;
import com.flasblog.back.model.PostModel;
import com.flasblog.back.model.UserModel;
import com.flasblog.back.persistence.entity.CommentaryEntity;
import com.flasblog.back.persistence.entity.PostEntity;
import com.flasblog.back.persistence.repository.CommentaryRepository;
import com.flasblog.back.persistence.repository.PostRepository;
import com.flasblog.back.service.implementations.PostImpl;
import com.flasblog.back.service.interfaces.PostInterface;
import com.flasblog.back.service.interfaces.UserInterface;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UnitTest {
  private PostInterface postService;
  @Mock private PostRepository postRepository;
  @Mock private CommentaryRepository commentaryRepository;
  @Mock private UserInterface userService;

  @BeforeEach
  public void init() {
    this.postService = new PostImpl(postRepository, commentaryRepository, userService);
  }

  @Test
  void testGetPostByIdReturnEmpty() {
    String mockPostId = "test";
    when(postRepository.findById(mockPostId)).thenReturn(Optional.empty());
    PostModel excpectedResult = new PostModel();
    PostModel actualResult = postService.getPostById(mockPostId);
    assertEquals(actualResult, excpectedResult);
  }

  @Test
  void testGetPostByIdReturnNonEmpty() {
    String mockContent = "MockContent";
    Integer id = 2;
    PostEntity mockPostEntity = PostEntity.builder().content(mockContent).id(id).build();
    when(postRepository.findById(id.toString())).thenReturn(Optional.of(mockPostEntity));
    PostModel excpectedResult = PostModel.builder().id(id.toString()).content(mockContent).build();
    PostModel actualResult = postService.getPostById(id.toString());
    assertEquals(actualResult, excpectedResult);
  }

  @Test
  void testGetAllPosts() {
    when(postRepository.findAll()).thenReturn(List.of());
    List<PostModel> actualResult = postService.getAllPosts();
    assertEquals(actualResult, List.of());
  }

  @Test
  void testCreatePost() {
    String mockContent = "MockContent";
    PostEntity mockEntity = PostEntity.builder().content(mockContent).build();
    Integer id = 2;
    UserModel mockUserModel = UserModel.builder().id(id).build();
    when(postRepository.save(any())).thenReturn(mockEntity);
    when(userService.getAuthenticatedUser()).thenReturn(mockUserModel);
    PostModel mockPost = PostModel.builder().content(mockContent).build();
    PostModel expectedResult = PostModel.builder().content(mockContent).build();
    PostModel actualResult = postService.createPost(mockPost);
    assertEquals(actualResult, expectedResult);
  }

  @Test
  void testCreatePostCommentary() {
    String mockCommentary = "mockCommentary";
    Integer id = 2;
    CommentaryEntity mockCommentaryEntity =
        CommentaryEntity.builder().commentary(mockCommentary).creatorId(id).build();
    CommentaryModel mockCommentaryModel =
        CommentaryModel.builder().commentary(mockCommentary).creatorId(id).build();
    UserModel mockUserModel = UserModel.builder().id(id).build();
    when(userService.getAuthenticatedUser()).thenReturn(mockUserModel);
    when(commentaryRepository.save(any())).thenReturn(mockCommentaryEntity);
    CommentaryModel expectedResult =
        CommentaryModel.builder().commentary(mockCommentary).creatorId(id).build();
    CommentaryModel actualResult = postService.createPostCommentary(mockCommentaryModel);
    assertEquals(actualResult, expectedResult);
  }
}

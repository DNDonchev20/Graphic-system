package com.example.graphic_system.services;

import com.example.graphic_system.models.Comment;
import com.example.graphic_system.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getCommentsByTaskId(Long taskId) {
        return commentRepository.findByTaskId(taskId);
    }

    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long id, Comment commentDetails) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.setContent(commentDetails.getContent());
        comment.setCreatedDate(commentDetails.getCreatedDate());
        comment.setAuthor(commentDetails.getAuthor());
        comment.setTask(commentDetails.getTask());

        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
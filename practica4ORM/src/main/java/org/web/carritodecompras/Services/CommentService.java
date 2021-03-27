package org.web.carritodecompras.Services;

import org.web.carritodecompras.Services.Connection.DataBaseRepository;
import org.web.carritodecompras.models.Comment;

public class CommentService extends DataBaseRepository<Comment> {
    private static CommentService commentService;

    public CommentService() {
        super(Comment.class);
    }

    public static CommentService getInstance(){
        if(commentService == null){
            commentService = new CommentService();
        }
        return commentService;
    }
}

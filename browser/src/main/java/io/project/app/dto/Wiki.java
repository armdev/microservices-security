package io.project.app.dto;


import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;



/**
 *
 * @author armena
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Wiki implements Serializable {
    private String id;   
    private String userId;
    private String title;
    private String header;
    private String content;
    private Date publishDate;
}

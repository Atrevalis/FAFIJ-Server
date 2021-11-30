package fafij.server.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import fafij.server.entity.Category;
import fafij.server.entity.Note;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@EnableTransactionManagement
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NoteDTO {
    private Long id;
    private String date;
    private Long sum;
    @JsonProperty("category")
    private CategoryDTO idCtgr;
    private String comment;

    DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    @Transactional
    public List<NoteDTO> getNoteDTOList(List<Note> noteList){
        List<NoteDTO> noteDTOList = new ArrayList<>();

        for(Note note : noteList){
            idCtgr = new CategoryDTO();
            NoteDTO noteDTO = new NoteDTO();
            noteDTO.setId(note.getId());
            noteDTO.setDate(df.format(note.getDate()));
            noteDTO.setSum(note.getSum());
            idCtgr.setName(note.getIdCtgr().getName());
            noteDTO.setIdCtgr(idCtgr);
            noteDTO.setComment(note.getComment());
            noteDTOList.add(noteDTO);
        }
        return noteDTOList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public CategoryDTO getIdCtgr() {
        return idCtgr;
    }

    public void setIdCtgr(CategoryDTO idCtgr) {
        this.idCtgr = idCtgr;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}

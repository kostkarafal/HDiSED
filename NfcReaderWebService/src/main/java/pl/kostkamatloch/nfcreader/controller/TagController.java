package pl.kostkamatloch.nfcreader.controller;

import pl.kostkamatloch.nfcreader.exception.ResourceNotFoundException;
import pl.kostkamatloch.nfcreader.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import pl.kostkamatloch.nfcreader.repository.TagRepository;

/**
 * Created by rajeevkumarsingh on 27/06/17.
 */
@RestController
@RequestMapping("/rest")
public class TagController {

    @Autowired
    TagRepository nfcTagRepository;

    @GetMapping("/tag")
    public List<Tag> getAllTags() {
        return nfcTagRepository.findAll();
    }

    @PostMapping("/tag")
    public Tag createTag(@Valid @RequestBody Tag tag) {
        return nfcTagRepository.save(tag);
    }

    @GetMapping("/tag/{id}")
    public Tag getTagById(@PathVariable(value = "id") Long tagId) {
        return nfcTagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));
    }

    @PutMapping("/tag/{id}")
    public Tag updateTag(@PathVariable(value = "id") Long tagId,
                                           @Valid @RequestBody Tag tagDetails) {

        Tag tag = nfcTagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

        tag.setTitle(tagDetails.getTitle());
        tag.setContent(tagDetails.getContent());
        
        Tag updatedTag = nfcTagRepository.save(tag);
        return updatedTag;
    }

    @DeleteMapping("/tag/{id}")
    public ResponseEntity<?> deleteTag(@PathVariable(value = "id") Long tagId) {
        Tag tag = nfcTagRepository.findById(tagId)
                .orElseThrow(() -> new ResourceNotFoundException("Tag", "id", tagId));

        nfcTagRepository.delete(tag);

        return ResponseEntity.ok().build();
    }
}

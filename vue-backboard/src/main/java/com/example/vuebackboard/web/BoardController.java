package com.example.vuebackboard.web;

import com.example.vuebackboard.entity.BoardEntity;
import com.example.vuebackboard.model.Header;
import com.example.vuebackboard.service.BoardService;
import com.example.vuebackboard.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@CrossOrigin
@RestController
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/board/list")
    public Header<List<BoardDto>> boardList(@PageableDefault(sort = {"idx"}) Pageable pageable){
        return boardService.getBoardList(pageable);
    }

    @GetMapping("/board/{id}")
    public BoardDto getBoard(@PathVariable Long id){
        return boardService.getBoard(id);
    }

    @PostMapping("/board")
    public BoardEntity create(@RequestBody BoardDto dto){
        return boardService.create(dto);
    }

    @PatchMapping("/board")
    public BoardEntity update(@RequestBody  BoardDto dto){
        return boardService.update(dto);
    }

    @DeleteMapping("/board/{id}")
    public void delete(@PathVariable  Long id){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>> COntroller  delete id ..... " + id);
        boardService.delete(id);
    }
}

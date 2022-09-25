package com.example.vuebackboard.service;

import com.example.vuebackboard.entity.BoardEntity;
import com.example.vuebackboard.entity.BoardRepository;
import com.example.vuebackboard.model.Header;
import com.example.vuebackboard.model.Pagination;
import com.example.vuebackboard.web.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class BoardService {
    private final BoardRepository boardRepository;

    public Header<List<BoardDto>> getBoardList(Pageable pageable) {
        List<BoardDto> dtos = new ArrayList<>();

        Page<BoardEntity> boardEntities = boardRepository.findAllByOrderByIdxDesc(pageable);
        for (BoardEntity entity : boardEntities) {
            BoardDto dto = BoardDto.builder()
                    .idx(entity.getIdx())
                    .author(entity.getAuthor())
                    .title(entity.getTitle())
                    .contents(entity.getContents())
                    .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                    .build();

            dtos.add(dto);
        }

        Pagination pagination = new Pagination(
                (int) boardEntities.getTotalElements()
                , pageable.getPageNumber()
                , pageable.getPageSize()
                , 10
        );
        System.out.println("Pagination: " + pagination.toString());

        return Header.OK(dtos, pagination);
    }
    public BoardDto getBoard(Long id){
        System.out.println("getBoard id ..... " + id);
        BoardEntity entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        System.out.println("getBoard entity.getTitle()..... " + entity.getTitle());

        return BoardDto.builder()
                .idx(entity.getIdx())
                .author(entity.getAuthor())
                .title(entity.getTitle())
                .contents(entity.getContents())
                .createdAt(entity.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss")))
                .build();
    }

    public BoardEntity create(BoardDto dto){
        BoardEntity entity = BoardEntity.builder()
                .idx(dto.getIdx())
                .author(dto.getAuthor())
                .title(dto.getTitle())
                .contents(dto.getContents())
                .createdAt(LocalDateTime.now())
                .build();
        return boardRepository.save(entity);
    }

    public BoardEntity update(BoardDto dto){
        BoardEntity entity = boardRepository.findById(dto.getIdx()).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        entity.setTitle(dto.getTitle());
        entity.setContents(dto.getContents());
        return boardRepository.save(entity);
    }

    public void delete(Long id){
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>  delete id ..... " + id);
        BoardEntity entity = boardRepository.findById(id).orElseThrow(() -> new RuntimeException("게시글을 찾을 수 없습니다."));
        System.out.println("service delete entity.getTitle()..... " + entity.getTitle());
        boardRepository.delete(entity);
    }
}

package com.ganado.inventario.service;

import com.ganado.inventario.dto.AnimalHistoryRequestDTO;
import com.ganado.inventario.dto.AnimalHistoryResponseDTO;
import com.ganado.inventario.mapper.AnimalHistoryMapper;
import com.ganado.inventario.model.Animal;
import com.ganado.inventario.model.AnimalHistory;
import com.ganado.inventario.repository.AnimalHistoryRepository;
import com.ganado.inventario.repository.AnimalRepository;
import com.lowagie.text.Font;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnimalHistoryServiceImpl implements AnimalHistoryService {

    private final AnimalHistoryRepository historyRepository;
    private final AnimalRepository animalRepository;
    private final AnimalHistoryMapper mapper;

    private Animal getAnimalOrThrow(UUID id) {
        return animalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Animal no encontrado con ID: " + id));
    }

    @Override
    public AnimalHistoryResponseDTO crear(AnimalHistoryRequestDTO dto) {
        Animal animal = getAnimalOrThrow(dto.getAnimalId());
        AnimalHistory entity = mapper.toEntity(dto, animal);
        AnimalHistory saved = historyRepository.save(entity);
        return mapper.toResponse(saved);
    }

    @Override
    public AnimalHistoryResponseDTO obtenerPorId(UUID id) {
        AnimalHistory e = historyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado con ID: " + id));
        return mapper.toResponse(e);
    }

    @Override
    public List<AnimalHistoryResponseDTO> listarTodos() {
        return historyRepository.findAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public List<AnimalHistoryResponseDTO> listarPorAnimal(UUID animalId) {
        // verifica existencia del animal para mejor UX
        getAnimalOrThrow(animalId);
        return historyRepository.findAllByAnimal_IdOrderByFechaCreacionDesc(animalId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public AnimalHistoryResponseDTO actualizar(UUID id, AnimalHistoryRequestDTO dto) {
        AnimalHistory existente = historyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado con ID: " + id));
        // obtener animal (no permitimos cambiar animal salvo que quieras)
        Animal animal = getAnimalOrThrow(dto.getAnimalId());
        mapper.updateEntity(existente, dto, animal);
        AnimalHistory saved = historyRepository.save(existente);
        return mapper.toResponse(saved);
    }

    @Override
    public void eliminar(UUID id) {
        AnimalHistory existente = historyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Historial no encontrado con ID: " + id));
        historyRepository.delete(existente);
    }

    @Override
    public byte[] exportarHistorialAnimalPdf(UUID animalId) {
        List<AnimalHistory> historial = historyRepository.findAllByAnimal_IdOrderByFechaCreacionDesc(animalId);
        Optional<Animal> animal = animalRepository.findById(animalId);
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, baos);

            document.open();

            // Título
            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);
            document.add(new Paragraph("Historial del Animal", titleFont));
            document.add(new Paragraph("Identificador: " + animal.get()));
            document.add(new Paragraph("ID: " + animalId));
            document.add(new Paragraph("Fecha: " + java.time.LocalDate.now()));
            document.add(Chunk.NEWLINE);

            // Tabla
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);

            table.addCell("Fecha");
            table.addCell("Día");
            table.addCell("Descripción");

            for (AnimalHistory h : historial) {
                table.addCell(h.getFechaCreacion().toLocalDate().toString());
                table.addCell(h.getDiaCreacion());
                table.addCell(h.getDescripcion());
            }

            document.add(table);
            document.close();

            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF", e);
        }
    }
}

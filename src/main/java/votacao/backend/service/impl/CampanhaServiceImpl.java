package votacao.backend.service.impl;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import votacao.backend.exceptions.CustomException;
import votacao.backend.model.dto.Campanha.CampanhaInfoDTO;
import votacao.backend.model.dto.Campanha.CampanhaDTO;
import votacao.backend.model.entity.Campanha;
import votacao.backend.repository.CampanhaRepository;
import votacao.backend.service.CampanhaService;
import votacao.backend.utils.DateConverter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Service
public class CampanhaServiceImpl implements CampanhaService {

    @Autowired
    private CampanhaRepository campanhaRepository;

    @Autowired
    private TaskScheduler taskScheduler;

    private ConcurrentHashMap<String, ScheduledFuture<?>> inicioVotacaoTasks = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, ScheduledFuture<?>> fimVotacaoTasks = new ConcurrentHashMap<>();


    @Override
    @Transactional
    public void novaCampanha(CampanhaDTO dto) {
        Optional<Campanha> campanhaOpt = this.campanhaRepository.findByTitulo(dto.titulo());
        if(campanhaOpt.isPresent())
            throw new CustomException("Já existe uma campanha com este título.", HttpStatus.CONFLICT);
        Campanha campanha = new Campanha();
        campanha.setId(UUID.randomUUID().toString());
        campanha.setTitulo(dto.titulo());
        campanha.setDescricao(dto.descricao());
        campanha.setVotacao_aberta(false);
        campanha.setData_criacao(LocalDateTime.now());
        campanha.setInicio_votacao(DateConverter.stringToLocalDateTime(dto.inicio_votacao()));
        campanha.setFim_votacao(DateConverter.stringToLocalDateTime(dto.fim_votacao()));
        campanha.setCandidatos(new ArrayList<>());
        Campanha cam = campanhaRepository.save(campanha);
        scheduleTasksForCampanha(cam);
    }

    @Override
    public CampanhaInfoDTO obterPorId(String id) {
        Campanha cam = buscarCampanha(id);
        return new CampanhaInfoDTO(
                cam.getTitulo(),
                cam.getDescricao(),
                cam.getVotacao_aberta(),
                cam.getData_criacao(),
                cam.getInicio_votacao(),
                cam.getFim_votacao(),
                cam.getCandidatos()
        );
    }

    @Override
    public List<Campanha> listar(Boolean votacao_aberta) {
        List<Campanha> lista = this.campanhaRepository.findAll();
        return lista
                .stream()
                .filter(item -> item.getVotacao_aberta().equals(votacao_aberta))
                .collect(Collectors.toList());
    }

    @Override
    public void atualizar(String id, CampanhaDTO dto) {
        Campanha campanha = buscarCampanha(id);
        LocalDateTime inicio = DateConverter.stringToLocalDateTime(dto.inicio_votacao());
        LocalDateTime fim = DateConverter.stringToLocalDateTime(dto.fim_votacao());
        campanha.setTitulo(dto.titulo());
        campanha.setDescricao(dto.descricao());
        campanha.setInicio_votacao(inicio);
        campanha.setFim_votacao(fim);
        campanhaRepository.save(campanha);
        scheduleTasksForCampanha(campanha);
    }

    private Campanha buscarCampanha(String id){
        Optional<Campanha> campanhaOpt = this.campanhaRepository.findById(id);
        if(campanhaOpt.isEmpty())
            throw new CustomException("Campanha não encontrada.", HttpStatus.NOT_FOUND);
        return campanhaOpt.get();
    }

    @PostConstruct
    private void scheduleInitialTasks() {
        List<Campanha> campanhas = campanhaRepository.findAll();
        for (Campanha campanha : campanhas) {
            scheduleTasksForCampanha(campanha);
        }
    }

    private void scheduleTasksForCampanha(Campanha campanha) {
        // Cancelar tarefas antigas, se existirem
        if (inicioVotacaoTasks.containsKey(campanha.getId())) {
            inicioVotacaoTasks.get(campanha.getId()).cancel(false);
        }
        if (fimVotacaoTasks.containsKey(campanha.getId())) {
            fimVotacaoTasks.get(campanha.getId()).cancel(false);
        }
        LocalDateTime now = LocalDateTime.now();
        if (campanha.getInicio_votacao().isAfter(now)) {
            ScheduledFuture<?> inicioFuture = taskScheduler.schedule(() -> abrirVotacao(campanha), convertToDateViaInstant(campanha.getInicio_votacao()));
            inicioVotacaoTasks.put(campanha.getId(), inicioFuture);
        }
        if (campanha.getFim_votacao().isAfter(now)) {
            ScheduledFuture<?> fimFuture = taskScheduler.schedule(() -> fecharVotacao(campanha), convertToDateViaInstant(campanha.getFim_votacao()));
            fimVotacaoTasks.put(campanha.getId(), fimFuture);
        }
    }

    private void abrirVotacao(Campanha campanha) {
        campanha.setVotacao_aberta(true);
        campanhaRepository.save(campanha);
        System.out.println("Votação aberta para a campanha: " + campanha.getTitulo());
    }

    private void fecharVotacao(Campanha campanha) {
        campanha.setVotacao_aberta(false);
        campanhaRepository.save(campanha);
        System.out.println("Votação fechada para a campanha: " + campanha.getTitulo());
    }

    private Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }
}

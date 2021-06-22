package com.nilesh.ipldashboard.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nilesh.ipldashboard.model.Match;
import com.nilesh.ipldashboard.model.Team;
import com.nilesh.ipldashboard.repository.MatchRepository;
import com.nilesh.ipldashboard.repository.TeamRepository;

@RestController
@CrossOrigin
public class TeamController {

	private TeamRepository teampRepository;

	private MatchRepository matchRepository;

	@Autowired
	public TeamController(TeamRepository teampRepository, MatchRepository matchRepository) {
		this.teampRepository = teampRepository;
		this.matchRepository = matchRepository;
	}

	@GetMapping("/team/{teamName}")
	public Team getTeam(@PathVariable String teamName) {
		Team team = this.teampRepository.findByTeamName(teamName);
		team.setMatches(this.matchRepository.findLatestMatchesByTeam(teamName, 4));
		return team;
	}

	@GetMapping("/team/{teamName}/matches")
	public List<Match> getMatchesForTeam(@PathVariable String teamName, @RequestParam int year) {
		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year + 1, 1, 1);

		return this.matchRepository.getMatchesByTeamBetweenDates(teamName, startDate,
				endDate);
	}

}

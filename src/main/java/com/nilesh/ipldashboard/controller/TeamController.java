package com.nilesh.ipldashboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.nilesh.ipldashboard.model.Team;
import com.nilesh.ipldashboard.repository.MatchRepository;
import com.nilesh.ipldashboard.repository.TeamRepository;

@RestController
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
}

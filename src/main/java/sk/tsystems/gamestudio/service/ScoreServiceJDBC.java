package sk.tsystems.gamestudio.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sk.tsystems.gamestudio.entity.Score;

/*
 * CREATE TABLE score (username VARCHAR(64) NOT NULL, game VARCHAR(64) NOT NULL, value INT NOT NULL);
 */

public class ScoreServiceJDBC implements ScoreService {
	private static final String URL = "jdbc:postgresql://localhost/gamestudio";
	private static final String LOGIN = "postgres";
	private static final String PASSWORD = "jahodka";
	private static final String INSERT = "INSERT INTO score (username, game, value) VALUES (?, ?, ?);";
	private static final String SELECT = "SELECT username, value FROM score WHERE game = ? ORDER BY value DESC LIMIT 10;";

	@Override
	public void addScore(Score score) {
		try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(INSERT)) {
			preparedStatement.setString(1, score.getUserName());
			preparedStatement.setString(2, score.getGame());
			preparedStatement.setInt(3, score.getValue());
			preparedStatement.executeUpdate();
		} catch (SQLException ex) {
			throw new GameStudioException(ex);
		}

	}

	@Override
	public List<Score> getTopScores(String game) {
		try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
				PreparedStatement preparedStatement = connection.prepareStatement(SELECT)) {
			preparedStatement.setString(1, game);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				List<Score> topScores = new ArrayList<>();
				while (resultSet.next()) {
					topScores.add(new Score(resultSet.getString(1), game, resultSet.getInt(2)));	
				}
				return topScores;
			}
		} catch (SQLException ex) {
			throw new GameStudioException(ex);
		}
	}
}

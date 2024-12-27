package ie.atu.sw;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Runner {

	public static void main(String[] args) throws Exception {
		String embeddingsFile = "./embeddings.txt";
		String google1000File = "./google-1000.txt";
		String inputFile = "./input.txt";
		CosineDistance cosineDistance = new CosineDistance();
		QuickSort quickSort = new QuickSort();

		new MainMenu().init();

		var embeddingsMap = new EmbeddingsMapper().map(embeddingsFile);
		var google1000Map = new Google1000Mapper().map(google1000File, embeddingsMap);
		var entries = google1000Map.entrySet().stream().toList();

		try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
			Files.lines(Paths.get(inputFile)).forEach(line -> {
				pool.execute(() -> {
					var words = line.split(" ");
					StringBuilder sb = new StringBuilder();
					List<double[]> results = new ArrayList<>();

					for (int i = 0; i < words.length; i++) {
						// Array to hold the word index and the cosine distance

						if (google1000Map.containsKey(words[i].toLowerCase())) {
							// System.out.println(words[i] + " is in google 1000 -> return word as is");
							sb.append(words[i] + " ");
							continue;
						}
						if (!embeddingsMap.containsKey(words[i].toLowerCase())) {
							System.out.println(words[i] + " -> is not is embeddings -> return word as is");
							sb.append(words[i] + " ");
							continue;
						}

						for (int j = 0; j < google1000Map.size(); j++) {
							double distance = cosineDistance.getDistance(embeddingsMap.get(words[i]),
									entries.get(j).getValue());
							if (distance > 0.7) {
								results.add(new double[] { (double) j, distance });
							}
						}

						if (results.size() > 0) {
							System.out.println(words[i]);
							System.out.println(results.size());
							quickSort.sort(results);
							var bestMatch = entries.get((int) results.get(results.size() - 1)[0]).getKey();
							System.out.println(bestMatch);
							System.out.println(Arrays.toString(results.get(results.size() - 1)));
							sb.append(bestMatch + " ");
						} else {
							sb.append(words[i] + " ");
						}
					}

					System.out.println(sb.toString());
				});
				// System.out.println(line);
			});
		}

	}

}
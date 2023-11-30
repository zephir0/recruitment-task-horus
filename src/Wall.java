import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Wall implements Structure {
    private final List<Block> blocks;

    public Wall(List<Block> blocks) {
        this.blocks = blocks;
    }

    @Override
    public Optional<Block> findBlockByColor(String color) {
        return blocks.stream()
                .flatMap(this::combineBlockStreams)
                .filter(block -> block.getColor().equals(color))
                .findFirst();
    }

    @Override
    public List<Block> findBlocksByMaterial(String material) {
        return blocks.stream()
                .flatMap(this::combineBlockStreams)
                .filter(block -> block.getMaterial().equals(material))
                .collect(Collectors.toList());
    }

    @Override
    public int count() {
        return blocks.stream()
                .flatMap(this::combineBlockStreams)
                .mapToInt(block -> 1)
                .sum();
    }

    private Stream<Block> combineBlockStreams(Block block) {
        if (block instanceof CompositeBlock) {
            return Stream.concat(Stream.of(block),
                    ((CompositeBlock) block).getBlocks().stream().flatMap(this::combineBlockStreams));
        } else {
            return Stream.of(block);
        }
    }

}
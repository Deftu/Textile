plugins {
    id("dev.deftu.gradle.multiversion-root")
}

preprocess {
    val forge12001 = createNode("1.20.1-forge", 1_20_01, "srg")
    val fabric12001 = createNode("1.20.1-fabric", 1_20_01, "yarn")
    val forge11904 = createNode("1.19.4-forge", 1_19_04, "srg")
    val fabric11904 = createNode("1.19.4-fabric", 1_19_04, "yarn")
    val forge11903 = createNode("1.19.3-forge", 1_19_03, "srg")
    val fabric11903 = createNode("1.19.3-fabric", 1_19_03, "yarn")
    val forge11902 = createNode("1.19.2-forge", 1_19_02, "srg")
    val fabric11902 = createNode("1.19.2-fabric", 1_19_02, "yarn")
    val forge11802 = createNode("1.18.2-forge", 1_18_02, "srg")
    val fabric11802 = createNode("1.18.2-fabric", 1_18_02, "yarn")
    val forge11701 = createNode("1.17.1-forge", 1_17_01, "srg")
    val fabric11701 = createNode("1.17.1-fabric", 1_17_01, "yarn")
    val forge11605 = createNode("1.16.5-forge", 1_16_05, "srg")
    val fabric11605 = createNode("1.16.5-fabric", 1_16_05, "yarn")
    val forge11202 = createNode("1.12.2-forge", 1_12_02, "srg")
    val forge10809 = createNode("1.8.9-forge", 1_08_09, "srg")

    forge12001.link(fabric12001)
    fabric12001.link(fabric11904)
    forge11904.link(fabric11904)
    fabric11904.link(fabric11903)
    forge11903.link(fabric11903)
    fabric11903.link(fabric11902)
    forge11902.link(fabric11902)
    fabric11902.link(fabric11802)
    forge11802.link(fabric11802)
    fabric11802.link(fabric11701)
    forge11701.link(fabric11701)
    fabric11701.link(fabric11605)
    fabric11605.link(forge11605)
    forge11605.link(forge11202)
    forge11202.link(forge10809, file("versions/1.12.2-forge+1.8.9-forge.txt"))
}

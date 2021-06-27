import Util._

addCommandAlias("l", "projects")
addCommandAlias("ll", "projects")
addCommandAlias("ls", "projects")
addCommandAlias("cd", "project")
addCommandAlias("root", "cd root")
addCommandAlias("c", "compile")
addCommandAlias("t", "test")
addCommandAlias("r", "run")
addCommandAlias("m", "show discoveredMainClasses")

onLoadMessage +=
  s"""|
      |╭──────────────────────────────────────────╮
      |│     List of defined ${styled("aliases")}              │
      |├─────────────┬────────────────────────────┤
      |│ ${styled("l")} | ${styled("ll")} | ${styled("ls")} │ projects                   │
      |│ ${styled("cd")}          │ project                    │
      |│ ${styled("root")}        │ cd root                    │
      |│ ${styled("m")}           │ show discoveredMainClasses │
      |│ ${styled("c")}           │ compile                    │
      |│ ${styled("t")}           │ test                       │
      |│ ${styled("r")}           │ run                        │
      |╰─────────────┴───────────────────────────-╯""".stripMargin
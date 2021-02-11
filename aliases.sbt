import Util._

addCommandAlias("ll", "projects")
addCommandAlias("cd", "project")
addCommandAlias("root", "cd root")
addCommandAlias("c", "compile")
addCommandAlias("t", "test")
addCommandAlias("r", "run")

onLoadMessage +=
  s"""|
      |───────────────────────────
      |  List of defined ${styled("aliases")}
      |────────┬──────────────────
      |${styled("ll")}      │ projects
      |${styled("cd")}      │ project
      |${styled("root")}    │ cd root
      |${styled("c")}       │ compile
      |${styled("t")}       │ test
      |${styled("r")}       │ run
      |────────┴──────────────────""".stripMargin
# Copilot instructions for `jgit-cookbook`

## Build and test

- Prefer the checked-in Gradle wrapper for local work:
  - `./gradlew check` — compile and run the root tests plus the `httpserver` subproject checks
  - `./gradlew test` — run the root JUnit suite
  - `./gradlew test --tests org.dstadler.jgit.api.ApiTest` — run one test class
  - `./gradlew test --tests org.dstadler.jgit.api.ApiTest.runSamples` — run one test method
- The repository also keeps a Maven build for the root module (there is no Maven wrapper in the repo):
  - `mvn test`
  - `mvn -Dtest=org.dstadler.jgit.api.ApiTest#runSamples test`
- Run the demo Git servlet application from the root project with:
  - `./gradlew :httpserver:run`

## High-level architecture

- This repository is a cookbook of runnable JGit snippets, not a single integrated application. Most production classes are small standalone examples with `main(String[] args)` entry points under `src/main/java/org/dstadler/jgit`.
- The package split is the main organizing principle:
  - `org.dstadler.jgit` contains basic repository open/create examples.
  - `org.dstadler.jgit.api` contains low-level JGit object/ref/tree/rev examples.
  - `org.dstadler.jgit.porcelain` contains higher-level `Git` command examples.
  - `org.dstadler.jgit.helper` contains shared helpers such as repository setup and progress logging.
  - `org.dstadler.jgit.unfinished` contains experiments/manual snippets that are not part of the normal smoke-test suite.
- The tests are smoke tests for the snippets, not deep unit tests. `ApiTest` and `PorcelainTest` execute many sample `main()` methods directly, and `JGitBugTest` preserves a specific upstream bug reproduction.
- Many `api` and `porcelain` samples inspect the checked-out `jgit-cookbook` repository itself via `CookbookHelper.openJGitCookbookRepository()`. Others create an isolated temporary repository via `CookbookHelper.createNewRepository()`.
- `httpserver` is a separate Gradle subproject that starts Jetty plus JGit's `GitServlet` and serves a temporary demo repository under `/repo/*`.
- Maven only covers the root snippet module. The `httpserver` demo is wired through Gradle subproject configuration in `settings.gradle`.

## Key conventions

- Keep snippets directly runnable: existing tests call `main(null)` on the sample classes, so avoid introducing required CLI arguments or external setup unless the matching smoke tests are updated too.
- Follow the existing repository-selection pattern:
  - use `CookbookHelper.openJGitCookbookRepository()` for read/inspection examples that intentionally work against this checkout
  - use `CookbookHelper.createNewRepository()` for write/mutation examples that should be self-contained
- Always close JGit resources with try-with-resources (`Repository`, `Git`, `RevWalk`, `ObjectReader`, and similar handles). Several snippets and tests rely on explicit closing to avoid file-handle and cleanup issues.
- Preserve temp-directory cleanup in write-oriented snippets. Existing examples usually delete temporary repositories at the end with `FileUtils.deleteDirectory(...)`.
- Do not casually change `master`-based refs or the shared public demo remote. Multiple snippets and tests hardcode `refs/heads/master`, `origin/master`, and `https://github.com/github/testrepo.git`.
- Leave authentication-dependent examples explicit rather than hiding them behind silent fallbacks. The SSH-based sample is allowed to fail when local SSH setup is missing, and the username/password sample is intentionally not part of `PorcelainTest`.

{
  description = "Hytale Kotlin development flake";

  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-25.11";
    flake-utils.url = "github:numtide/flake-utils";
  };

  outputs = { self, nixpkgs, flake-utils }:
    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = nixpkgs.legacyPackages.${system};

        jdk = pkgs.openjdk25;
        idea = pkgs.jetbrains.idea-oss;
      in
      {
        devShells.default = pkgs.mkShell {
          name = "hytale-kotlin-dev";

          buildInputs = with pkgs; [
            jdk
            kotlin
            ktlint
            
            gradle
            maven

            frp

            idea
            bind
          ];

          env = {
            JAVA_HOME = "${jdk}/lib/openjdk";
            JDK_HOME = "${jdk}/lib/openjdk";
            GRADLE_OPTS = "-Dorg.gradle.java.home=${jdk}/lib/openjdk";
            KOTLIN_HOME = "${pkgs.kotlin}";
          };

          shellHook = ''
            echo "══════════════════════════════════════════"
            echo "  JAVA_HOME: $JAVA_HOME"
            echo "  Java:   $(java --version 2>&1 | head -1)"
            echo "  Kotlin: $(kotlin -version 2>&1 | head -1)"
            echo "  Gradle: $(gradle --version 2>/dev/null | grep -oP 'Gradle \K[\d.]+')"
            echo "══════════════════════════════════════════"
          '';
        };

        apps = {
          idea = {
            type = "app";
            program = "${idea}/bin/idea-oss";
          };
          default = self.apps.${system}.idea;
        };
        
        formatter = pkgs.nixpkgs-fmt;
      });
}

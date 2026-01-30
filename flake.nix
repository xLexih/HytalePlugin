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
        jbr25 = pkgs.stdenv.mkDerivation rec {
          pname = "jetbrains-jdk";
          javaVersion = "25.0.1";
          version = "b285.56";

          src = pkgs.fetchurl {
            url = "https://cache-redirector.jetbrains.com/intellij-jbr/jbr_jcef-${javaVersion}-linux-x64-${version}.tar.gz";
            sha256 = "sha256-PrQgnFt9kSXdpCNh68KtPCgv7BFgvaJVPxKiiI6lZhw=";
          };

          nativeBuildInputs = [ pkgs.autoPatchelfHook ];

          buildInputs = with pkgs; [
            stdenv.cc.cc.lib zlib alsa-lib

            xorg.libX11 xorg.libXext xorg.libXi xorg.libXrender xorg.libXtst
            xorg.libXxf86vm xorg.libXcomposite xorg.libXdamage xorg.libXrandr
            xorg.libXcursor
            libGL libglvnd mesa libdrm
            fontconfig freetype cairo pango
            glib dbus atk at-spi2-atk cups
            wayland libxkbcommon
            nspr nss systemd
          ];

          installPhase = ''
            mkdir -p $out
            cp -r * $out/
          '';

          dontStrip = true;
        };

        idea = pkgs.jetbrains.idea-oss;

      in
      {
        devShells.default = pkgs.mkShell {
          packages = with pkgs; [
            jbr25
            kotlin
            ktlint
            gradle
            maven
            frp
            idea
            bind
          ];

          JAVA_HOME = "${jbr25}";
          GRADLE_OPTS = "-Dorg.gradle.java.home=${jbr25}";

          shellHook = ''
            echo "══════════════════════════════════════════"
            echo "  JAVA_HOME: $JAVA_HOME"
            echo "  Java:   $(java --version 2>&1 | head -1)"
            echo "  Kotlin: $(kotlin -version 2>&1 | head -1)"
            echo "  Gradle: $(gradle --version 2>/dev/null | grep -oP 'Gradle \K[\d.]+')"
            echo "══════════════════════════════════════════"
          '';
        };

        apps.default = {
          type = "app";
          program = "${pkgs.writeShellScript "idea-here" ''
            exec ${idea}/bin/idea-oss "$(pwd)" "$@"
          ''}";
        };

        formatter = pkgs.nixpkgs-fmt;
      });
}


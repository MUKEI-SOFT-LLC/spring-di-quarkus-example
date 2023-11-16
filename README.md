# spring-di-quarkus-example

このプロジェクトは、[springフレームワークに依存し開発されたライブラリ](https://github.com/MUKEI-SOFT-LLC/spring-world-ex)をQuarkusから利用するにあたり、
ネイティブビルドができるようにするまで過程をブランチで提示しています。

## ブランチについて

* step1 [0.0.1]
  Springフレームワークで開発されたライブラリを素の状態で利用しています。masterブランチとの差分はありません。
* step2 [0.0.2]
  Bean解決ができないので、ライブラリ側に空のbeans.xmlを追加し、Bean解決を試みています。
  依存するライブラリプロジェクトのバージョンだけ上げています。
* step3 [0.0.3]
  ライブラリ側のDIの記述があいまいであるため、明示的にBeanを宣言しています。
* step4 [0.0.4]
  `org.springframework.core.env.Environment` が解決できないため、このプロジェクトからDIを試みています。
* step5 [0.0.5]
  `org.springframework.core.env.Environment` に依存したコードのネイティブビルドができないため、独自のクラスをライブラリに追加しています。
  依存するライブラリプロジェクトのバージョンだけ上げています。このステップからネイティブビルドができるようになります。
* step6 [0.0.6]
  logback + slf4jでログが出力されないため、application.propertiesを調整しています。
* step7 [0.0.7]
  `@PostConstruct` `@PreDestroy` を使ったBeanライフサイクルのフックメソッドが呼ばれないため、Quarkusのライフサイクルに則り、ライブラリ側のライフサイクルメソッドを呼び出しています。
  
なお、各ブランチのベースブランチは、それぞれの前のstepブランチです。([]内は、ライブラリのjarのバージョン)
たとえば、step2はstep1をベースブランチにしています。

## JVMモードの起動について

`jp.co.mukeisoftllc.ex.BootApplication` をIDEから起動します。
gradleから起動する場合は、 `gradlew quarkusDev` とします。

## ネイティブビルドについて

dockerのマルチステージビルドでコンテナでイメージをビルドしています。
./src/main/docker/Dockerfileの１行目に実行コマンドは記載しています。
コマンドの実行は、このプロジェクトのカレントディレクトリです。

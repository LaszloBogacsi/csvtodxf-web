import React, {Component} from 'react';
import './App.css';
import CsvToDxf from "./csvtodxf/csvtodxf";
import Version from "./version/version";

class App extends Component {
  render() {
    return (
      <div className="App">
          <CsvToDxf/>
          <Version className={"footer"}/>
      </div>
    );
  }
}

export default App;

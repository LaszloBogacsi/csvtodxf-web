import React, {Component} from 'react'
import Download from "./Download";

class Result extends Component {
    constructor(props) {
        super(props);
        this.state = {
            convertResponse:
                {downloadId: "cc13bc11-8086-47fb-87ee-4ca058b1cb48",
                durationInMillies: 1,
                fileSize: 1,
                numberOfLinesConverted: 1
                }

        }
    }

    componentWillReceiveProps(nextProps) {
        console.log(nextProps);
        if (nextProps.convertResponse.response !== this.state.convertResponse) {
            this.setState({convertResponse: nextProps.convertResponse.response});
        }
    }

    render() {
        const response = this.state.convertResponse;
        return (
            <div>
                <h2>Conversion Results</h2>
                <div>
                    <table>
                        <thead>
                        <tr>
                            <th>Stuff</th>
                            <th>Value</th>
                        </tr>
                        </thead>
                        <tbody>
                        {Object.keys(response).map((key, i) => {
                            return ([
                                <tr key={i}>
                                    <td>{key}</td>
                                    <td>{response[key]}</td>
                                </tr>
                            ])
                        })
                        }
                        </tbody>
                    </table>
                </div>
                <Download downloadId={response.downloadId}/>
            </div>

        )
    }
}

export default Result;
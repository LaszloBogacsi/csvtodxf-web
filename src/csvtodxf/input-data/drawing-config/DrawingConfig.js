import React, {Component} from 'react'
import * as Rx from "rxjs-compat";
import {Button} from 'semantic-ui-react'


class DrawingConfig extends Component {
    constructor(props) {
        super(props);
        this.state = {
            drawingId: this.props.drawingId,
            fileName: this.props.fileName,
            separator: this.separators[0].value,
            textHeight: 1.0,
            doPrintId: false,
            doPrintCoords: false,
            doPrintCode: false,
            doPrintHeight: false,
            doPrint3D: true,
            doLayerByCode: false

        };
        console.log(this.state.fileName);
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleChange(event) {
        const target = event.target;
        const value = (target.type === 'checkbox') ? target.checked : target.value;
        this.setState({[target.name]: value});
    }

    handleSubmit(event) {
        event.preventDefault();
        const requestBody = JSON.stringify(this.state);
        const url = 'http://localhost:9090/convert';
        Rx.Observable.fromPromise(fetch(url, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: requestBody
        })).flatMap(res => res.json())
            .subscribe(response => {
                this.props.onConvertResponse(response);
                    console.log(response)
                },
                error => console.log(error)
            )
    }

    separators = [
        {name: 'comma', value: ','},
        {name: 'semicolon', value: ';'}
    ];

    componentWillReceiveProps(nextProps) {
        if (nextProps.fileName !== this.state.fileName) {
            this.setState({fileName: nextProps.fileName});
        }
        if (nextProps.drawingId !== this.state.drawingId) {
            this.setState({drawingId: nextProps.drawingId});
        }
    }

    render() {
        return (
            <div>
                <form onSubmit={this.handleSubmit}>
                    <div>
                        <p>Separator</p>
                        <select onChange={this.handleChange} value={this.state.separator} name="separator">
                            {this.separators.map(separator => <option key={separator.name}
                                                                      value={separator.value}>{separator.value}</option>)}
                        </select>
                    </div>
                    <div>
                        <p>Text Height</p>
                        <input type="text" name="textHeight" value={this.state.textHeight}
                               onChange={this.handleChange}/>
                    </div>
                    <div>
                        <input type="checkbox" name="doPrint3D" onChange={this.handleChange}/>3D<br/>
                    </div>
                    <div>
                        <input type="checkbox" name="doPrintId" onChange={this.handleChange}/>Point Number<br/>
                        <input type="checkbox" name="doPrintHeight" onChange={this.handleChange}/>Height<br/>
                        <input type="checkbox" name="doPrintCoords" onChange={this.handleChange}/>Coordinate<br/>
                        <input type="checkbox" name="doPrintCode" onChange={this.handleChange}/>Code<br/>
                        <input type="checkbox" name="doLayerByCode" onChange={this.handleChange}/>Layer By Code<br/>
                    </div>
                    <Button type="submit" content="Go" primary/>
                </form>
            </div>
        );

    }
}

export default DrawingConfig;
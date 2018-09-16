import React, {Component} from 'react'
import * as Rx from "rxjs-compat";
import {Button, Checkbox, Form, Input, Select} from 'semantic-ui-react'


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

    handleChange(event, data) {
        console.log(data);
        const value = (data.type === 'checkbox') ? data.checked : data.value;
        this.setState({[data.name]: value});

    }

    toggleLoader() {
        this.props.onToggleLoader();
    }


    handleSubmit(event) {
        event.preventDefault();
        const requestBody = JSON.stringify(this.state);
        const url = 'http://localhost:9090/convert';
        this.toggleLoader();
        setTimeout(() => {
            Rx.Observable.fromPromise(fetch(url, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: requestBody
            })).flatMap(res => res.json())
                .subscribe(response => {
                        this.toggleLoader();
                        this.props.onConvertResponse(response);
                        console.log(response)
                    },
                    error => console.log(error)
                )
        }, 2000)
    }

    separators = [
        {text: 'comma', value: ','},
        {text: 'semicolon', value: ';'}
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
                <Form onSubmit={this.handleSubmit}>
                    <Form.Group widths="equal">
                        <Form.Field name="separator" control={Select} options={this.separators} label="Separator" onChange={this.handleChange} value={this.state.separator}/>
                        <Form.Field name="textHeight" control={Input} label="Text Height" value={this.state.textHeight} onChange={this.handleChange}/>
                    </Form.Group>
                    <Form.Field name="3D" control={Checkbox} label="3D" onChange={this.handleChange}/>
                    <label>What to show on drawing</label>
                    <Form.Group grouped>
                        <Form.Field  name="doPrintId" control={Checkbox} onChange={this.handleChange} label="Point Number" defaultChecked/>
                        <Form.Field  name="doPrintHeight" control={Checkbox} onChange={this.handleChange} label="Height" defaultChecked/>
                        <Form.Field  name="doPrintCoords" control={Checkbox} onChange={this.handleChange} label="Coordinate"/>
                        <Form.Field  name="doPrintCode" control={Checkbox} onChange={this.handleChange} label="Code"/>
                        <Form.Field  name="doLayerByCode" control={Checkbox} onChange={this.handleChange} label="Layer By Code"/>


                    </Form.Group>
                    <Form.Field primary control={Button}>Convert</Form.Field>
                </Form>
            </div>
        );

    }
}

export default DrawingConfig;
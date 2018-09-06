import React, {Component} from 'react'
import * as Rx from "rxjs-compat";
import {Button, Checkbox, Container, Dropdown, Grid, Input} from 'semantic-ui-react'


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
                <form onSubmit={this.handleSubmit}>
                    <Container>
                        <Grid>
                        <Grid.Row verticalAlign='middle'>
                            <Grid.Column width={4}>
                                <p>Separator</p>
                            </Grid.Column>
                            <Grid.Column width={5}>
                                <Dropdown fluid selection options={this.separators} onChange={this.handleChange} value={this.state.separator} name="separator"/>
                            </Grid.Column>
                            <Grid.Column width={6}>
                                <Checkbox name="doPrint3D" onChange={this.handleChange} label="3D"/>
                            </Grid.Column>
                        </Grid.Row>
                        </Grid>
                    </Container>
                    <div>
                        <Grid>
                            <Grid.Row>
                                <Grid.Column verticalAlign='middle' width={4}>
                                    <p>Text Height</p>
                                </Grid.Column>
                                <Grid.Column width={5}>
                                    <Input fluid type="text" name="textHeight" value={this.state.textHeight}
                                           onChange={this.handleChange}/>

                                </Grid.Column>
                            </Grid.Row>
                        </Grid>
                    </div>
                    <Container>
                        <Grid columns={1}>
                            <Grid.Row>
                                <Grid.Column>
                                    <Checkbox name="doPrintId" onChange={this.handleChange} label="Point Number"/>
                                </Grid.Column>
                            </Grid.Row>
                            <Grid.Row>
                                <Grid.Column>
                                    <Checkbox name="doPrintHeight" onChange={this.handleChange} label="Height"/>
                                </Grid.Column>
                            </Grid.Row>
                            <Grid.Row>
                                <Grid.Column>
                                    <Checkbox name="doPrintCoords" onChange={this.handleChange} label="Coordinate"/>
                                </Grid.Column>
                            </Grid.Row>
                            <Grid.Row>
                                <Grid.Column>
                                    <Checkbox name="doPrintCode" onChange={this.handleChange} label="Code"/>
                                </Grid.Column>
                            </Grid.Row>
                            <Grid.Row>
                                <Grid.Column>
                                    <Checkbox name="doLayerByCode" onChange={this.handleChange} label="Layer By Code"/>
                                </Grid.Column>
                            </Grid.Row>
                        </Grid>
                    </Container>
                    <Button type="submit" content="Go" primary/>
                </form>
            </div>
        );

    }
}

export default DrawingConfig;
package com.tigerbrokers.quant.gateway.tiger;

import com.alibaba.fastjson.JSONObject;
import com.tigerbrokers.quant.gateway.Gateway;
import com.tigerbrokers.quant.model.data.Asset;
import com.tigerbrokers.quant.model.data.Order;
import com.tigerbrokers.quant.model.data.Position;
import com.tigerbrokers.quant.model.data.Tick;
import com.tigerbrokers.stock.openapi.client.socket.ApiComposeCallback;
import com.tigerbrokers.stock.openapi.client.struct.SubscribedSymbol;

/**
 * Description:
 *
 * @author kevin
 * @date 2019/08/20
 */
public class TigerSubscribeApi implements ApiComposeCallback {

  private Gateway gateway;

  public TigerSubscribeApi(Gateway gateway) {
    this.gateway = gateway;
  }

  @Override
  public void error(String errorMsg) {
    gateway.log("{} subscribeError {}", gateway.getGatewayName(), errorMsg);
  }

  @Override
  public void error(int id, int errorCode, String errorMsg) {
    gateway.log("{} subscribeError {}", gateway.getGatewayName(), errorMsg);
  }

  @Override
  public void connectionClosed() {
    gateway.log("{} connectionClosed ", gateway.getGatewayName());
  }

  @Override
  public void connectionAck() {
    gateway.log("{} connectionAck ", gateway.getGatewayName());
  }

  @Override
  public void connectionAck(int serverSendInterval, int serverReceiveInterval) {
    gateway.log("{} connectionAck sendInterval:{},receiveInterval:{} ", gateway.getGatewayName(), serverSendInterval,
        serverReceiveInterval);
  }

  @Override
  public void hearBeat(String heartBeatContent) {
    gateway.log("{} connectionHeartBeat {}", gateway.getGatewayName(), heartBeatContent);
  }

  @Override
  public void serverHeartBeatTimeOut(String channelIdAsLongText) {
    gateway.log("{} ConnectionHeartBeatTimeout {}", gateway.getGatewayName(), channelIdAsLongText);
  }

  @Override
  public void subscribeEnd(JSONObject jsonObject) {
    gateway.log("{} subscribeEnd {}", gateway.getGatewayName(), jsonObject);
  }

  @Override
  public void cancelSubscribeEnd(JSONObject jsonObject) {
    gateway.log("{} cancelSubscribeEnd {}", gateway.getGatewayName(), jsonObject);
  }

  @Override
  public void getSubscribedSymbolEnd(SubscribedSymbol subscribedSymbol) {
    gateway.log("{} subscribeSymbols {}", gateway.getGatewayName(), subscribedSymbol);
  }

  @Override
  public void orderStatusChange(JSONObject jsonObject) {
    Order order = jsonObject.toJavaObject(Order.class);
    gateway.onOrder(order);
  }

  @Override
  public void positionChange(JSONObject jsonObject) {
    Position position = jsonObject.toJavaObject(Position.class);
    gateway.onPosition(position);
  }

  @Override
  public void assetChange(JSONObject jsonObject) {
    Asset asset = jsonObject.toJavaObject(Asset.class);
    gateway.onAsset(asset);
  }

  @Override
  public void quoteChange(JSONObject jsonObject) {
    Tick tick = new Tick();
    tick.jsonToTick(jsonObject);
    gateway.onTick(tick);
  }

  @Override
  public void optionChange(JSONObject jsonObject) {
    Tick tick = new Tick();
    tick.jsonToTick(jsonObject);
    gateway.onTick(tick);
  }

  @Override
  public void futureChange(JSONObject jsonObject) {
    Tick tick = new Tick();
    tick.jsonToTick(jsonObject);
    gateway.onTick(tick);
  }

  @Override
  public void askBidChange(JSONObject jsonObject) {
    //UnImplement
  }
}
